package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.connection.ConnectionFactory;
import br.com.solara.model.vo.Medicao;

// Classe para métodos CRUD das medições realizadas pelos sensores IoT
public class MedicaoDAO {
    
    private final Connection minhaConexao;

    public MedicaoDAO() throws ClassNotFoundException, SQLException {
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public void inserir(Medicao medicao) throws SQLException {
        String sql = "INSERT INTO tb_medicoes (id_sensor, tipo_medicao, valor_medicao, data_hora_medicao) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, medicao.getIdSensor());
        stmt.setString(2, medicao.getTipoMedicao());
        stmt.setInt(3, medicao.getValorMedicao());
        stmt.setTimestamp(4, Timestamp.valueOf(medicao.getDataHoraMedicao()));

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            medicao.setIdMedicao(rs.getInt(1));
        }

        stmt.close();
    }

    // Delete
    public String deletar(int idMedicao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_medicoes WHERE id_medicao = ?");
        
        stmt.setInt(1, idMedicao);
        stmt.execute();
        stmt.close();
        return "Medição deletada com sucesso!";
    }

    // Update
    public String atualizar(Medicao medicao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE tb_medicoes SET id_sensor = ?, tipo_medicao = ?, valor_medicao = ?, data_hora_medicao = ? WHERE id_medicao = ?");
        
        stmt.setInt(1, medicao.getIdSensor());
        stmt.setString(2, medicao.getTipoMedicao());
        stmt.setInt(3, medicao.getValorMedicao());
        stmt.setTimestamp(4, Timestamp.valueOf(medicao.getDataHoraMedicao()));
        stmt.setInt(5, medicao.getIdMedicao());
        
        stmt.executeUpdate();
        stmt.close();
        return "Medição atualizada com sucesso!";
    }

    // Select All
    public List<Medicao> selecionarTodos() throws SQLException {
        List<Medicao> listaMedicoes = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_medicao, id_sensor, tipo_medicao, valor_medicao, data_hora_medicao FROM tb_medicoes");
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Medicao medicao = new Medicao();
            medicao.setIdMedicao(rs.getInt("id_medicao"));
            medicao.setIdSensor(rs.getInt("id_sensor"));
            medicao.setTipoMedicao(rs.getString("tipo_medicao"));
            medicao.setValorMedicao(rs.getInt("valor_medicao"));
            medicao.setDataHoraMedicao(rs.getTimestamp("data_hora_medicao").toLocalDateTime());
            listaMedicoes.add(medicao);
        }
        
        stmt.close();
        return listaMedicoes;
    }

    // Select
    public Medicao selecionar(int idMedicao) throws SQLException {
        Medicao medicao = null;
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_medicao, id_sensor, tipo_medicao, valor_medicao, data_hora_medicao FROM tb_medicoes WHERE id_medicao = ?");
        
        stmt.setInt(1, idMedicao);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            medicao = new Medicao();
            medicao.setIdMedicao(rs.getInt("id_medicao"));
            medicao.setIdSensor(rs.getInt("id_sensor"));
            medicao.setTipoMedicao(rs.getString("tipo_medicao"));
            medicao.setValorMedicao(rs.getInt("valor_medicao"));
            medicao.setDataHoraMedicao(rs.getTimestamp("data_hora_medicao").toLocalDateTime());
        }
        
        stmt.close();
        return medicao;
    }
}
