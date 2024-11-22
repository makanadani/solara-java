package br.com.solara.model.bo;

import br.com.solara.model.dao.SensorDAO;
import br.com.solara.model.vo.Sensor;

import java.sql.SQLException;
import java.util.ArrayList;

public class SensorBO {

    SensorDAO sensorDAO = null;

    // Inserir
    public void inserirBO(Sensor sensor) throws ClassNotFoundException, SQLException, Exception {
        SensorDAO sensorDAO = new SensorDAO();

        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(sensor);

        // 2. Verifica se o id_tipo_fonte é válido apenas para sensores de produção.
        if (sensor.getIdTipoFonte() != 0 && !"Produção".equalsIgnoreCase(sensor.getTipoSensor())) {
            throw new Exception("id_tipo_fonte só pode ser associado a sensores de produção.");
        }

        // Realiza a inserção do sensor.
        sensorDAO.inserir(sensor);
    }

    // Atualizar
    public void atualizarBO(Sensor sensor) throws ClassNotFoundException, SQLException, Exception {
        SensorDAO sensorDAO = new SensorDAO();

        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(sensor);

        // 2. Verifica se o id_tipo_fonte é válido apenas para sensores de produção.
        if (sensor.getIdTipoFonte() != 0 && !"Produção".equalsIgnoreCase(sensor.getTipoSensor())) {
            throw new Exception("id_tipo_fonte só pode ser associado a sensores de produção.");
        }

        // Realiza a atualização do sensor.
        sensorDAO.atualizar(sensor);
    }

    // Selecionar todos
    public ArrayList<Sensor> selecionarBO() throws ClassNotFoundException, SQLException {
        SensorDAO sensorDAO = new SensorDAO();

        // Retorna a lista de todos os sensores.
        return (ArrayList<Sensor>) sensorDAO.selecionarTodos();
    }

    // Deletar
    public void deletarBO(int idSensor) throws ClassNotFoundException, SQLException {
        SensorDAO sensorDAO = new SensorDAO();

        // Regras de negócio:
        // 1. Verifica se o sensor existe antes de excluir.
        Sensor sensor = sensorDAO.selecionar(idSensor);
        if (sensor == null) {
            throw new SQLException("Sensor não encontrado para exclusão.");
        }

        // Realiza a exclusão do sensor.
        sensorDAO.deletar(idSensor);
    }

    // Validação de campos obrigatórios
    private void validarCamposObrigatorios(Sensor sensor) throws Exception {
        // Verifica se o tipo de sensor foi informado.
        if (sensor.getTipoSensor() == null || sensor.getTipoSensor().trim().isEmpty()) {
            throw new Exception("O tipo de sensor é obrigatório.");
        }

        // Verifica se o id da comunidade é válido.
        if (sensor.getIdComunidade() <= 0) {
            throw new Exception("O id da comunidade é obrigatório.");
        }
    }
}
