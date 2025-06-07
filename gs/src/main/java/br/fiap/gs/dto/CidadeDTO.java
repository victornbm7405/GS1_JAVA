package br.fiap.gs.dto;

public class CidadeDTO {
    private Long id;
    private String nome;
    private String estado;
    private Double latitude;
    private Double longitude;

    public CidadeDTO() { }

    public CidadeDTO(Long id, String nome, String estado, Double latitude, Double longitude) {
        this.id        = id;
        this.nome      = nome;
        this.estado    = estado;
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
