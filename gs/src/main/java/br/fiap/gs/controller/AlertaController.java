package br.fiap.gs.controller;

import br.fiap.gs.model.Alerta;
import br.fiap.gs.service.AlertaService;
import br.fiap.gs.security.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final JwtUtil jwtUtil;

    public AlertaController(AlertaService alertaService, JwtUtil jwtUtil) {
        this.alertaService = alertaService;
        this.jwtUtil = jwtUtil;
    }

    private boolean isTokenInvalid(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ") || !jwtUtil.validateToken(authHeader.substring(7));
    }

    @GetMapping
    public ResponseEntity<Page<Alerta>> listarTodos(Pageable pageable,
                                                    @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        return ResponseEntity.ok(alertaService.listarTodosPaginado(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @PostMapping("/{idCidade}")
    public ResponseEntity<Alerta> gerarAlerta(@PathVariable Long idCidade,
                                              @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        return ResponseEntity.ok(alertaService.gerarAlertaPorCidade(idCidade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerta> atualizar(@PathVariable Long id,
                                            @RequestParam("novaTemperatura") double novaTemperatura,
                                            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        return ResponseEntity.ok(alertaService.atualizar(id, novaTemperatura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id,
                                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (isTokenInvalid(authHeader)) return ResponseEntity.status(401).build();

        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
