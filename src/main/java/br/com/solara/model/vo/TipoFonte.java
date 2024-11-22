package br.com.solara.model.vo;

// Classe referente aos tipos de fontes de energia (TABELA E DADOS FORNECIDOS PELA DISCIPLINA BUILDING RELATIONAL DATABASE)
public class TipoFonte {
    private int idTipoFonte;
    private String nomeFonte;

    public TipoFonte() {
    }

    public TipoFonte(int idTipoFonte, String nomeFonte) {
        this.idTipoFonte = idTipoFonte;
        this.nomeFonte = nomeFonte;
    }

    public int getIdTipoFonte() {
        return idTipoFonte;
    }

    public void setIdTipoFonte(int idTipoFonte) {
        this.idTipoFonte = idTipoFonte;
    }

    public String getNomeFonte() {
        return nomeFonte;
    }

    public void setNomeFonte(String nomeFonte) {
        this.nomeFonte = nomeFonte;
    }

    @Override
    public String toString() {
        return "TipoFonte{" +
                "idTipoFonte=" + idTipoFonte +
                ", nomeFonte='" + nomeFonte + '\'' +
                '}';
    }
}
