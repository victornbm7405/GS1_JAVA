package br.fiap.gs.controller;

import br.fiap.gs.exception.ResourceNotFoundException;
import br.fiap.gs.model.Cidade;
import br.fiap.gs.repository.CidadeRepository;
import br.fiap.gs.dto.CidadeDTO;
import br.fiap.gs.security.JwtService;
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
    public ResponseEntity<List<CidadeDTO>> listar(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("🔑 Token recebido: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);
        boolean valid = JwtService.validateTokenStatic(token);
        System.out.println("✅ Token válido? " + valid);
        if (!valid) {
            return ResponseEntity.status(401).build();
        }

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
        System.out.println("🔑 Token recebido: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);
        boolean valid = JwtService.validateTokenStatic(token);
        System.out.println("✅ Token válido? " + valid);
        if (!valid) {
            return ResponseEntity.status(401).build();
        }

        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com id: " + id));
        return ResponseEntity.ok(toDTO(cidade));
    }

    private CidadeDTO toDTO(Cidade cidade) {
        CidadeDTO dto = new CidadeDTO();
        dto.setId(cidade.getId());
        dto.setNome(cidade.getNome());
        dto.setEstado(cidade.getEstado());
        return dto;
    }
}
