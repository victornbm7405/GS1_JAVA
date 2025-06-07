package br.fiap.gs.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GS_MENSAGEM")
public class Alerta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 4000)
    private String descricao;

    @Column(name = "NIVEL_RISCO", length = 10)
    private String nivelRisco;

    @Column(name = "TEMPERATURA")
    private Double temperatura;

    @Column(name = "TIPO_ALERTA", nullable = false)
    private String tipoAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CIDADE", nullable = false)
    private Cidade cidade;

    public Alerta() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(String nivelRisco) { this.nivelRisco = nivelRisco; }

    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    public String getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(String tipoAlerta) { this.tipoAlerta = tipoAlerta; }

    public Cidade getCidade() { return cidade; }
    public void setCidade(Cidade cidade) { this.cidade = cidade; }
}
