package br.com.solara.model.vo;

import java.time.LocalDateTime;

public class Medicao {
    private int idMedicao;
    private int idComunidade;
    private int idSensor;
    private String tipoMedicao;
    private int valorMedicao;
    private LocalDateTime dataHoraMedicao;

    public Medicao() {
    }

    public Medicao(int idMedicao, int idComunidade, int idSensor, 
    		String tipoMedicao, int valorMedicao, LocalDateTime dataHoraMedicao) {
        this.idMedicao = idMedicao;
        this.idComunidade = idComunidade;
        this.idSensor = idSensor;
        this.tipoMedicao = tipoMedicao;
        this.valorMedicao = valorMedicao;
        this.dataHoraMedicao = dataHoraMedicao;
    }

    public int getIdMedicao() {
        return idMedicao;
    }

    public void setIdMedicao(int idMedicao) {
        this.idMedicao = idMedicao;
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public String getTipoMedicao() {
        return tipoMedicao;
    }

    public void setTipoMedicao(String tipoMedicao) {
        this.tipoMedicao = tipoMedicao;
    }

    public int getValorMedicao() {
        return valorMedicao;
    }

    public void setValorMedicao(int valorMedicao) {
        this.valorMedicao = valorMedicao;
    }

    public LocalDateTime getDataHoraMedicao() {
        return dataHoraMedicao;
    }

    public void setDataHoraMedicao(LocalDateTime dataHoraMedicao) {
        this.dataHoraMedicao = dataHoraMedicao;
    }

    @Override
    public String toString() {
        return "Medicao{" +
                "idMedicao=" + idMedicao +
                ", idComunidade=" + idComunidade +
                ", idSensor=" + idSensor +
                ", tipoMedicao='" + tipoMedicao + '\'' +
                ", valorMedicao=" + valorMedicao +
                ", dataHoraMedicao=" + dataHoraMedicao +
                '}';
    }
}
