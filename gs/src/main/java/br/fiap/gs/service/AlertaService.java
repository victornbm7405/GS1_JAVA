package br.fiap.gs.service;

import br.fiap.gs.exception.ResourceNotFoundException;
import br.fiap.gs.model.Alerta;
import br.fiap.gs.model.Cidade;
import br.fiap.gs.repository.AlertaRepository;
import br.fiap.gs.repository.CidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepo;
    private final CidadeRepository cidadeRepo;
    private final RestTemplate restTemplate;
    private final OpenMeteoService openMeteoService;

    public AlertaService(AlertaRepository alertaRepo,
                         CidadeRepository cidadeRepo,
                         RestTemplate restTemplate,
                         OpenMeteoService openMeteoService) {
        this.alertaRepo = alertaRepo;
        this.cidadeRepo = cidadeRepo;
        this.restTemplate = restTemplate;
        this.openMeteoService = openMeteoService;
    }

    public List<Alerta> listarTodos() {
        return alertaRepo.findAll();
    }

    public Page<Alerta> listarTodosPaginado(Pageable pageable) {
        return alertaRepo.findAll(pageable);
    }

    public Alerta buscarPorId(Long id) {
        return alertaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("⚠️ Nenhum alerta climático foi localizado com o identificador: " + id));
    }

    public Alerta gerarAlertaPorCidade(Long idCidade) {
        Cidade cidade = cidadeRepo.findById(idCidade)
                .orElseThrow(() -> new ResourceNotFoundException("🌍 A cidade informada não foi encontrada no sistema: " + idCidade));

        double temperatura = openMeteoService.getTemperaturaAtual(cidade.getLatitude(), cidade.getLongitude());

        String descricao;
        String risco;
        if (temperatura >= 35) {
            descricao = "Temperaturas acima de 35°C. Evite exposição ao sol entre 10h e 16h. Hidrate-se e use roupas leves.";
            risco     = "ALTO";
        } else if (temperatura <= 5) {
            descricao = "Temperatura inferior a 5°C. Vista-se com roupas adequadas, evite locais abertos e proteja pessoas vulneráveis.";
            risco     = "ALTO";
        } else {
            descricao = "Condições térmicas normais. Aproveite o clima com segurança, mantendo-se informado sobre mudanças.";
            risco     = "BAIXO";
        }

        Alerta alerta = new Alerta();
        alerta.setCidade(cidade);
        alerta.setDescricao(descricao);
        alerta.setNivelRisco(risco);
        alerta.setTemperatura(temperatura);
        alerta.setTipoAlerta("TEMPERATURA");

        return alertaRepo.save(alerta);
    }

    public Alerta atualizar(Long id, double novaTemperatura) {
        Alerta existente = alertaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("🔍 Alerta não encontrado para atualização: " + id));

        String descricao;
        String risco;
        if (novaTemperatura >= 35) {
            descricao = "Temperaturas acima de 35°C. Evite exposição ao sol entre 10h e 16h. Hidrate-se e use roupas leves.";
            risco     = "ALTO";
        } else if (novaTemperatura <= 5) {
            descricao = "Temperatura inferior a 5°C. Vista-se com roupas adequadas, evite locais abertos e proteja pessoas vulneráveis.";
            risco     = "ALTO";
        } else {
            descricao = "Condições térmicas normais. Aproveite o clima com segurança, mantendo-se informado sobre mudanças.";
            risco     = "BAIXO";
        }

        existente.setTemperatura(novaTemperatura);
        existente.setDescricao(descricao);
        existente.setNivelRisco(risco);

        return alertaRepo.save(existente);
    }

    public void deletar(Long id) {
        if (!alertaRepo.existsById(id)) {
            throw new ResourceNotFoundException("⚠️ Alerta não encontrado para remoção: " + id);
        }
        alertaRepo.deleteById(id);
    }
}
