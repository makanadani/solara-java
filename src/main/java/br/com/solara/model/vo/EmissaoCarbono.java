package br.com.solara.model.vo;

public class EmissaoCarbono {
    private int idEmissao;
    private int idTipoFonte;
    private double emissao;

    public EmissaoCarbono() {
    }

    public EmissaoCarbono(int idEmissao, int idTipoFonte, double emissao) {
        this.idEmissao = idEmissao;
        this.idTipoFonte = idTipoFonte;
        this.emissao = emissao;
    }

    // Getters e Setters
    public int getIdEmissao() {
        return idEmissao;
    }

    public void setIdEmissao(int idEmissao) {
        this.idEmissao = idEmissao;
    }

    public int getIdTipoFonte() {
        return idTipoFonte;
    }

    public void setIdTipoFonte(int idTipoFonte) {
        this.idTipoFonte = idTipoFonte;
    }

    public double getEmissao() {
        return emissao;
    }

    public void setEmissao(double emissao) {
        this.emissao = emissao;
    }

    @Override
    public String toString() {
        return "EmissaoCarbono{" +
                "idEmissao=" + idEmissao +
                ", idTipoFonte=" + idTipoFonte +
                ", emissao=" + emissao +
                '}';
    }
}
