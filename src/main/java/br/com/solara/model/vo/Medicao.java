package br.com.solara.model.vo;

import java.time.LocalDateTime;

// Classe referente às medições realizadas pelos sensores IoT de produção, armazenamento e consumo de energia
public class Medicao {

    private int idMedicao;
    private int idSensor;
    private String tipoMedicao;
    private Integer valorMedicao;
    private LocalDateTime dataHoraMedicao;

    public Medicao() {
    }

    public Medicao(int idMedicao, int idSensor, String tipoMedicao, int valorMedicao, LocalDateTime dataHoraMedicao) {
        this.idMedicao = idMedicao;
        this.setIdSensor(idSensor);
        this.setTipoMedicao(tipoMedicao);
        this.setValorMedicao(valorMedicao);
        this.setDataHoraMedicao(dataHoraMedicao);
    }

    public int getIdMedicao() {
        return idMedicao;
    }

    public void setIdMedicao(int idMedicao) {
        this.idMedicao = idMedicao;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        if (idSensor <= 0) {
            throw new IllegalArgumentException("O ID do sensor deve ser maior que zero.");
        }
        this.idSensor = idSensor;
    }

    public String getTipoMedicao() {
        return tipoMedicao;
    }

    public void setTipoMedicao(String tipoMedicao) {
        if (tipoMedicao == null || tipoMedicao.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de medição não pode estar vazio.");
        }
        if (!tipoMedicao.equals("Produção") && !tipoMedicao.equals("Armazenamento") && !tipoMedicao.equals("Consumo")) {
            throw new IllegalArgumentException("O tipo de medição deve ser 'Produção', 'Armazenamento' ou 'Consumo'.");
        }
        this.tipoMedicao = tipoMedicao;
    }

    public int getValorMedicao() {
        return valorMedicao;
    }

    public void setValorMedicao(Integer valorMedicao) {
        if (valorMedicao < 0) {
            throw new IllegalArgumentException("O valor da medição não pode ser negativo.");
        }
        this.valorMedicao = valorMedicao;
    }

    public LocalDateTime getDataHoraMedicao() {
        return dataHoraMedicao;
    }

    public void setDataHoraMedicao(LocalDateTime dataHoraMedicao) {
        if (dataHoraMedicao == null) {
            throw new IllegalArgumentException("A data e hora da medição não podem ser nulas.");
        }
        this.dataHoraMedicao = dataHoraMedicao;
    }

    @Override
    public String toString() {
        return "Medicao{" +
                "idMedicao=" + idMedicao +
                ", idSensor=" + idSensor +
                ", tipoMedicao='" + tipoMedicao + '\'' +
                ", valorMedicao=" + valorMedicao +
                ", dataHoraMedicao=" + dataHoraMedicao +
                '}';
    }
}
