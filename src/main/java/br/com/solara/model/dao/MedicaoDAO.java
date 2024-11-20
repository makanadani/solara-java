package br.com.solara.model.dao;

import br.com.solara.model.vo.Medicao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicaoDAO {

    private final Connection connection;

    // Construtor
    public MedicaoDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir uma nova medição)
    public void inserir(Medicao medicao) throws SQLException {
        String sql = "INSERT INTO tb_medicoes (id_comunidade, id_sensor, tipo_medicao, valor_medicao, data_hora_medicao) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, medicao.getIdComunidade());
            stmt.setInt(2, medicao.getIdSensor());
            stmt.setString(3, medicao.getTipoMedicao());
            stmt.setInt(4, medicao.getValorMedicao());
            stmt.setTimestamp(5, Timestamp.valueOf(medicao.getDataHoraMedicao()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    medicao.setIdMedicao(rs.getInt(1));
                }
            }
        }
    }

    // Read (Buscar uma medição pelo ID)
    public Medicao buscarPorId(int idMedicao) throws SQLException {
        String sql = "SELECT id_medicao, id_comunidade, id_sensor, tipo_medicao, valor_medicao, data_hora_medicao " +
                     "FROM tb_medicoes WHERE id_medicao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Medicao(
                        rs.getInt("id_medicao"),
                        rs.getInt("id_comunidade"),
                        rs.getInt("id_sensor"),
                        rs.getString("tipo_medicao"),
                        rs.getInt("valor_medicao"),
                        rs.getTimestamp("data_hora_medicao").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    // Read (Listar todas as medições)
    public List<Medicao> listarTodas() throws SQLException {
        List<Medicao> medicoes = new ArrayList<>();
        String sql = "SELECT id_medicao, id_comunidade, id_sensor, tipo_medicao, valor_medicao, data_hora_medicao FROM tb_medicoes";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                medicoes.add(new Medicao(
                    rs.getInt("id_medicao"),
                    rs.getInt("id_comunidade"),
                    rs.getInt("id_sensor"),
                    rs.getString("tipo_medicao"),
                    rs.getInt("valor_medicao"),
                    rs.getTimestamp("data_hora_medicao").toLocalDateTime()
                ));
            }
        }
        return medicoes;
    }

    // Update (Atualizar dados de uma medição)
    public void atualizar(Medicao medicao) throws SQLException {
        String sql = "UPDATE tb_medicoes SET id_comunidade = ?, id_sensor = ?, tipo_medicao = ?, " +
                     "valor_medicao = ?, data_hora_medicao = ? WHERE id_medicao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, medicao.getIdComunidade());
            stmt.setInt(2, medicao.getIdSensor());
            stmt.setString(3, medicao.getTipoMedicao());
            stmt.setInt(4, medicao.getValorMedicao());
            stmt.setTimestamp(5, Timestamp.valueOf(medicao.getDataHoraMedicao()));
            stmt.setInt(6, medicao.getIdMedicao());
            stmt.executeUpdate();
        }
    }

    // Delete (Remover uma medição pelo ID)
    public void deletar(int idMedicao) throws SQLException {
        String sql = "DELETE FROM tb_medicoes WHERE id_medicao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMedicao);
            stmt.executeUpdate();
        }
    }
}
