package br.com.solara.model.vo;

// Classe referente aos projetos sustentáveis (TABELA E DADOS FORNECIDOS PELA DISCIPLINA BUILDING RELATIONAL DATABASE)
public class ProjetoSustentavel {
    private int idProjeto;
    private Integer idTipoFonte;
    private Integer idRegiao;
    private String descricaoProjeto;
    private Double custoProjeto;
    private String statusProjeto;

    public ProjetoSustentavel() {
    }

    public ProjetoSustentavel(int idProjeto, Integer idTipoFonte, Integer idRegiao, 
                              String descricaoProjeto, Double custoProjeto, String statusProjeto) {
        this.idProjeto = idProjeto;
        this.idTipoFonte = idTipoFonte;
        this.idRegiao = idRegiao;
        this.descricaoProjeto = descricaoProjeto;
        this.custoProjeto = custoProjeto;
        this.statusProjeto = statusProjeto;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Integer getIdTipoFonte() {
        return idTipoFonte;
    }

    public void setIdTipoFonte(Integer idTipoFonte) {
        this.idTipoFonte = idTipoFonte;
    }

    public Integer getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(Integer idRegiao) {
        this.idRegiao = idRegiao;
    }

    public String getDescricaoProjeto() {
        return descricaoProjeto;
    }

    public void setDescricaoProjeto(String descricaoProjeto) {
        this.descricaoProjeto = descricaoProjeto;
    }

    public Double getCustoProjeto() {
        return custoProjeto;
    }

    public void setCustoProjeto(Double custoProjeto) {
        this.custoProjeto = custoProjeto;
    }

    public String getStatusProjeto() {
        return statusProjeto;
    }

    public void setStatusProjeto(String statusProjeto) {
        this.statusProjeto = statusProjeto;
    }

    @Override
    public String toString() {
        return "ProjetoSustentavel{" +
                "idProjeto=" + idProjeto +
                ", idTipoFonte=" + idTipoFonte +
                ", idRegiao=" + idRegiao +
                ", descricaoProjeto='" + descricaoProjeto + '\'' +
                ", custoProjeto=" + custoProjeto +
                ", statusProjeto='" + statusProjeto + '\'' +
                '}';
    }
}
