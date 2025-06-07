package br.fiap.gs.controller;

import br.fiap.gs.model.Alerta;
import br.fiap.gs.service.AlertaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
@SecurityRequirement(name = "bearerAuth")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public ResponseEntity<Page<Alerta>> listarTodos(
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(alertaService.listarTodosPaginado(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @PostMapping("/{idCidade}")
    public ResponseEntity<Alerta> gerarAlerta(@PathVariable Long idCidade) {
        Alerta alerta = alertaService.gerarAlertaPorCidade(idCidade);
        return ResponseEntity.ok(alerta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerta> atualizar(
            @PathVariable Long id,
            @RequestParam("novaTemperatura") double novaTemperatura
    ) {
        return ResponseEntity.ok(alertaService.atualizar(id, novaTemperatura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
