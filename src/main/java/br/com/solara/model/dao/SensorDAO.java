package br.com.solara.model.dao;

import br.com.solara.model.vo.Sensor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SensorDAO {

    private final Connection connection;

    // Construtor
    public SensorDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir um novo sensor)
    public void inserir(Sensor sensor) throws SQLException {
        String sql = "INSERT INTO tb_sensores (id_comunidade, id_tipo_fonte, tipo_sensor, descricao_sensor) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sensor.getIdComunidade());
            stmt.setInt(2, sensor.getIdTipoFonte());
            stmt.setString(3, sensor.getTipoSensor());
            stmt.setString(4, sensor.getDescricaoSensor());
            stmt.executeUpdate();

            // Recupera a chave gerada
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sensor.setIdSensor(rs.getInt(1));
                }
            }
        }
    }

    // Read (Buscar um sensor pelo ID)
    public Sensor buscarPorId(int idSensor) throws SQLException {
        String sql = "SELECT id_sensor, id_comunidade, id_tipo_fonte, tipo_sensor, descricao_sensor FROM tb_sensores WHERE id_sensor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSensor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Sensor(
                        rs.getInt("id_sensor"),
                        rs.getInt("id_comunidade"),
                        rs.getInt("id_tipo_fonte"),
                        rs.getString("tipo_sensor"),
                        rs.getString("descricao_sensor")
                    );
                }
            }
        }
        return null; // Retorna null se o sensor não for encontrado
    }

    // Read (Listar todos os sensores)
    public List<Sensor> listarTodos() throws SQLException {
        List<Sensor> sensores = new ArrayList<>();
        String sql = "SELECT id_sensor, id_comunidade, id_tipo_fonte, tipo_sensor, descricao_sensor FROM tb_sensores";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sensores.add(new Sensor(
                    rs.getInt("id_sensor"),
                    rs.getInt("id_comunidade"),
                    rs.getInt("id_tipo_fonte"),
                    rs.getString("tipo_sensor"),
                    rs.getString("descricao_sensor")
                ));
            }
        }
        return sensores;
    }

    // Update (Atualizar dados de um sensor)
    public void atualizar(Sensor sensor) throws SQLException {
        String sql = "UPDATE tb_sensores SET id_comunidade = ?, id_tipo_fonte = ?, tipo_sensor = ?, descricao_sensor = ? WHERE id_sensor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sensor.getIdComunidade());
            stmt.setInt(2, sensor.getIdTipoFonte());
            stmt.setString(3, sensor.getTipoSensor());
            stmt.setString(4, sensor.getDescricaoSensor());
            stmt.setInt(5, sensor.getIdSensor());
            stmt.executeUpdate();
        }
    }

    // Delete (Remover um sensor pelo ID)
    public void deletar(int idSensor) throws SQLException {
        String sql = "DELETE FROM tb_sensores WHERE id_sensor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSensor);
            stmt.executeUpdate();
        }
    }
}
