package br.fiap.gs.controller;

import br.fiap.gs.exception.ResourceNotFoundException;
import br.fiap.gs.model.Cidade;
import br.fiap.gs.repository.CidadeRepository;
import br.fiap.gs.dto.CidadeDTO;
import br.fiap.gs.security.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    private boolean isTokenInvalid(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ") || !jwtUtil.validateToken(authHeader.substring(7));
    }

    @GetMapping
    public ResponseEntity<List<CidadeDTO>> listar(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        List<CidadeDTO> lista = cidadeRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> buscarPorId(@PathVariable Long id,
                                                 @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com id: " + id));
        return ResponseEntity.ok(toDTO(cidade));
    }

    @PostMapping
    public ResponseEntity<CidadeDTO> criar(@RequestBody CidadeDTO dto,
                                           @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        Cidade nova = toEntity(dto);
        Cidade salva = cidadeRepository.save(nova);
        return ResponseEntity.ok(toDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long id, @RequestBody CidadeDTO dto,
                                               @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        Cidade existente = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com id: " + id));

        existente.setNome(dto.getNome());
        existente.setEstado(dto.getEstado());
        existente.setLatitude(dto.getLatitude());
        existente.setLongitude(dto.getLongitude());

        Cidade atualizado = cidadeRepository.save(existente);
        return ResponseEntity.ok(toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id,
                                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        if (!cidadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cidade não encontrada com id: " + id);
        }
        cidadeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CidadeDTO toDTO(Cidade cidade) {
        CidadeDTO dto = new CidadeDTO();
        dto.setId(cidade.getId());
        dto.setNome(cidade.getNome());
        dto.setEstado(cidade.getEstado());
        dto.setLatitude(cidade.getLatitude());
        dto.setLongitude(cidade.getLongitude());
        return dto;
    }

    private Cidade toEntity(CidadeDTO dto) {
        Cidade cidade = new Cidade();
        cidade.setId(dto.getId());
        cidade.setNome(dto.getNome());
        cidade.setEstado(dto.getEstado());
        cidade.setLatitude(dto.getLatitude());
        cidade.setLongitude(dto.getLongitude());
        return cidade;
    }
}
