package br.fiap.gs.controller;

import br.fiap.gs.exception.ResourceNotFoundException;
import br.fiap.gs.model.Cidade;
import br.fiap.gs.repository.CidadeRepository;
import br.fiap.gs.dto.CidadeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @GetMapping
    public ResponseEntity<List<CidadeDTO>> listar() {
        List<CidadeDTO> lista = cidadeRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> buscarPorId(@PathVariable Long id) {
        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada: " + id));
        return ResponseEntity.ok(toDTO(cidade));
    }

    @PostMapping
    public ResponseEntity<CidadeDTO> criar(@RequestBody CidadeDTO dto) {
        Cidade nova = toEntity(dto);
        Cidade salva = cidadeRepository.save(nova);
        return ResponseEntity.ok(toDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long id, @RequestBody CidadeDTO dto) {
        Cidade existente = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada: " + id));

        existente.setNome(dto.getNome());
        existente.setEstado(dto.getEstado());
        existente.setLatitude(dto.getLatitude());
        existente.setLongitude(dto.getLongitude());

        Cidade atualizado = cidadeRepository.save(existente);
        return ResponseEntity.ok(toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!cidadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cidade não encontrada: " + id);
        }
        cidadeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CidadeDTO toDTO(Cidade cidade) {
        return new CidadeDTO(
                cidade.getId(),
                cidade.getNome(),
                cidade.getEstado(),
                cidade.getLatitude(),
                cidade.getLongitude()
        );
    }

    private Cidade toEntity(CidadeDTO dto) {
        // Cria via construtor padrão e depois popula pelos setters
        Cidade c = new Cidade();
        c.setId(dto.getId());
        c.setNome(dto.getNome());
        c.setEstado(dto.getEstado());
        c.setLatitude(dto.getLatitude());
        c.setLongitude(dto.getLongitude());
        return c;
    }
}
