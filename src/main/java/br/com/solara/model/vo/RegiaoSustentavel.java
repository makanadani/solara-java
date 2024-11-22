package br.com.solara.model.vo;

// Classe referente às regiões sustentáveis (TABELA E DADOS FORNECIDOS PELA DISCIPLINA BUILDING RELATIONAL DATABASE)
public class RegiaoSustentavel {
    private int idRegiao;
    private String nomeRegiao;

    public RegiaoSustentavel() {
    }

    public RegiaoSustentavel(int idRegiao, String nomeRegiao) {
        this.idRegiao = idRegiao;
        this.nomeRegiao = nomeRegiao;
    }

    public int getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(int idRegiao) {
        this.idRegiao = idRegiao;
    }

    public String getNomeRegiao() {
        return nomeRegiao;
    }

    public void setNomeRegiao(String nomeRegiao) {
        this.nomeRegiao = nomeRegiao;
    }

    @Override
    public String toString() {
        return "RegiaoSustentavel{" +
                "idRegiao=" + idRegiao +
                ", nomeRegiao='" + nomeRegiao + '\'' +
                '}';
    }
}
