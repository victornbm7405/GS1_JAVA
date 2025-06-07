package br.fiap.gs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlertaDTO {

    private Long id;

    @NotBlank(message = "O nível de alerta é obrigatório")
    private String tipoAlerta;

    @NotBlank(message = "A descrição do alerta é obrigatória")
    private String descricao;

    private Double temperatura;

    @NotNull(message = "A informação de cidade é obrigatória")
    private CidadeDTO cidade;

    public AlertaDTO() {
    }

    public AlertaDTO(Long id, String tipoAlerta, String descricao, Double temperatura, CidadeDTO cidade) {
        this.id = id;
        this.tipoAlerta = tipoAlerta;
        this.descricao = descricao;
        this.temperatura = temperatura;
        this.cidade = cidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public CidadeDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDTO cidade) {
        this.cidade = cidade;
    }
}
