package br.com.solara.model.bo;

import br.com.solara.model.dao.MedicaoDAO;
import br.com.solara.model.dao.SensorDAO;
import br.com.solara.model.vo.Medicao;
import br.com.solara.model.vo.Sensor;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class MedicaoBO {

    private final MedicaoDAO medicaoDAO;
    private final SensorDAO sensorDAO;

    // Construtor
    public MedicaoBO(Connection connection) {
        this.medicaoDAO = new MedicaoDAO(connection);
        this.sensorDAO = new SensorDAO(connection);
    }

    // Realizar medições para todos os sensores IoT
    public void realizarMedicoesAutomatizadas() throws SQLException {
        List<Sensor> sensores = sensorDAO.listarTodos();

        // Para cada sensor, gera e insere uma medição
        for (Sensor sensor : sensores) {
            Medicao medicao = gerarMedicao(sensor);
            medicaoDAO.inserir(medicao);
        }
    }

    // Gerar uma medição para um sensor específico
    private Medicao gerarMedicao(Sensor sensor) {
        Random random = new Random();
        int valorMedicao = random.nextInt(500) + 1;

        return new Medicao(
                0,
                sensor.getIdComunidade(),
                sensor.getIdSensor(),
                sensor.getTipoSensor(),
                valorMedicao,
                LocalDateTime.now()
        );
    }
}
