package br.com.solara.model.vo;

public class ComunidadeProjeto {

    private int idComunidade;
    private int idProjeto;

    public ComunidadeProjeto() {
    }

    public ComunidadeProjeto(int idComunidade, int idProjeto) {
        this.idComunidade = idComunidade;
        this.idProjeto = idProjeto;
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    @Override
    public String toString() {
        return "ComunidadeProjeto{" +
                "idComunidade=" + idComunidade +
                ", idProjeto=" + idProjeto +
                '}';
    }
}
