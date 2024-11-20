package br.com.solara.model.vo;

public class Sensor {
    private int idSensor;
    private int idComunidade;
    private int idTipoFonte;
    private String tipoSensor;
    private String descricaoSensor;

    public Sensor() {
    }

    public Sensor(int idSensor, int idComunidade, int idTipoFonte, String tipoSensor, String descricaoSensor) {
        this.idSensor = idSensor;
        this.idComunidade = idComunidade;
        this.idTipoFonte = idTipoFonte;
        this.tipoSensor = tipoSensor;
        this.descricaoSensor = descricaoSensor;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }

    public int getIdTipoFonte() {
        return idTipoFonte;
    }

    public void setIdTipoFonte(int idTipoFonte) {
        this.idTipoFonte = idTipoFonte;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
		this.tipoSensor = tipoSensor;
	}

	public String getDescricaoSensor() {
        return descricaoSensor;
    }

    public void setDescricaoSensor(String descricaoSensor) {
        this.descricaoSensor = descricaoSensor;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "idSensor=" + idSensor +
                ", idComunidade=" + idComunidade +
                ", idTipoFonte=" + idTipoFonte +
                ", tipoSensor='" + tipoSensor + '\'' +
                ", descricaoSensor='" + descricaoSensor + '\'' +
                '}';
    }
}
