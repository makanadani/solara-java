package br.com.solara.model.bo;

import br.com.solara.exceptions.Exceptions.ValidationException;
import br.com.solara.model.vo.Sensor;

public class SensorBO {

    // Método para validar o tipo de sensor
    public void validarTipoSensor(Sensor sensor) {
        String tipoSensor = sensor.getTipoSensor();
        if (!tipoSensor.equals("Produção") && !tipoSensor.equals("Armazenamento") && !tipoSensor.equals("Consumo")) {
            throw new ValidationException("Tipo de sensor inválido. Deve ser 'Produção', 'Armazenamento' ou 'Consumo'.");
        }
    }

    // Método para verificar compatibilidade do sensor com o tipo de fonte
    public boolean verificarCompatibilidadeComFonte(Sensor sensor, String tipoFonte) {
        if (sensor.getTipoSensor().equals("Produção") && !tipoFonte.equals("Solar")) {
            throw new ValidationException("Sensores de produção só podem ser associados a fontes do tipo Solar.");
        }
        return true;
    }
}
