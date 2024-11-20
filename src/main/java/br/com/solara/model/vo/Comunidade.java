package br.com.solara.model.vo;

public class Comunidade {
    private int idComunidade;
    private int idEmpresa;
    private int idRegiao;
    private Integer protocoloAtendimentoComunidade;
    private String nomeComunidade;
    private double latitudeComunidade;
    private double longitudeComunidade;

    public Comunidade() {
    }

    public Comunidade(int idComunidade, int idEmpresa, int idRegiao, Integer protocoloAtendimentoComunidade,
                      String nomeComunidade, double latitudeComunidade, double longitudeComunidade) {
        this.idComunidade = idComunidade;
        this.idEmpresa = idEmpresa;
        this.idRegiao = idRegiao;
        this.protocoloAtendimentoComunidade = protocoloAtendimentoComunidade;
        this.nomeComunidade = nomeComunidade;
        this.latitudeComunidade = latitudeComunidade;
        this.longitudeComunidade = longitudeComunidade;
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(int idRegiao) {
        this.idRegiao = idRegiao;
    }

    public Integer getProtocoloAtendimentoComunidade() {
        return protocoloAtendimentoComunidade;
    }

    public void setProtocoloAtendimentoComunidade(Integer protocoloAtendimentoComunidade) {
        this.protocoloAtendimentoComunidade = protocoloAtendimentoComunidade;
    }

    public String getNomeComunidade() {
        return nomeComunidade;
    }

    public void setNomeComunidade(String nomeComunidade) {
        this.nomeComunidade = nomeComunidade;
    }

    public double getLatitudeComunidade() {
        return latitudeComunidade;
    }

    public void setLatitudeComunidade(double latitudeComunidade) {
        this.latitudeComunidade = latitudeComunidade;
    }

    public double getLongitudeComunidade() {
        return longitudeComunidade;
    }

    public void setLongitudeComunidade(double longitudeComunidade) {
        this.longitudeComunidade = longitudeComunidade;
    }

    @Override
    public String toString() {
        return "Comunidade{" +
                "idComunidade=" + idComunidade +
                ", idEmpresa=" + idEmpresa +
                ", idRegiao=" + idRegiao +
                ", protocoloAtendimentoComunidade=" + protocoloAtendimentoComunidade +
                ", nomeComunidade='" + nomeComunidade + '\'' +
                ", latitudeComunidade=" + latitudeComunidade +
                ", longitudeComunidade=" + longitudeComunidade +
                '}';
    }
}
