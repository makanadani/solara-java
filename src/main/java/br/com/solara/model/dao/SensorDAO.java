package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.connection.ConnectionFactory;
import br.com.solara.model.vo.Sensor;

// Classe para métodos CRUD dos sensores IoT
public class SensorDAO {

    public Connection minhaConexao;

    public SensorDAO() throws ClassNotFoundException, SQLException {
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(Sensor sensor) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO tb_sensores (id_comunidade, id_tipo_fonte, tipo_sensor) VALUES (?, ?, ?)", 
                PreparedStatement.RETURN_GENERATED_KEYS);
        
        stmt.setInt(1, sensor.getIdComunidade());
        stmt.setInt(2, sensor.getIdTipoFonte());
        stmt.setString(3, sensor.getTipoSensor());
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            sensor.setIdSensor(rs.getInt(1));
        }
        
        stmt.close();
        return "Sensor cadastrado com sucesso!";
    }

    // Delete
    public String deletar(int idSensor) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_sensores WHERE id_sensor = ?");
        
        stmt.setInt(1, idSensor);
        stmt.execute();
        stmt.close();
        return "Sensor deletado com sucesso!";
    }

    // Update
    public String atualizar(Sensor sensor) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE tb_sensores SET id_comunidade = ?, id_tipo_fonte = ?, tipo_sensor = ? WHERE id_sensor = ?");
        
        stmt.setInt(1, sensor.getIdComunidade());
        stmt.setInt(2, sensor.getIdTipoFonte());
        stmt.setString(3, sensor.getTipoSensor());
        stmt.setInt(4, sensor.getIdSensor());
        stmt.executeUpdate();
        stmt.close();
        return "Sensor atualizado com sucesso!";
    }

    // Select All
    public List<Sensor> selecionarTodos() throws SQLException {
        List<Sensor> listaSensores = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_sensor, id_comunidade, id_tipo_fonte, tipo_sensor FROM tb_sensores");
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Sensor sensor = new Sensor();
            sensor.setIdSensor(rs.getInt("id_sensor"));
            sensor.setIdComunidade(rs.getInt("id_comunidade"));
            sensor.setIdTipoFonte(rs.getInt("id_tipo_fonte"));
            sensor.setTipoSensor(rs.getString("tipo_sensor"));
            listaSensores.add(sensor);
        }
        
        stmt.close();
        return listaSensores;
    }

    // Select
    public Sensor selecionar(int idSensor) throws SQLException {
        Sensor sensor = null;
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_sensor, id_comunidade, id_tipo_fonte, tipo_sensor FROM tb_sensores WHERE id_sensor = ?");
        
        stmt.setInt(1, idSensor);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            sensor = new Sensor();
            sensor.setIdSensor(rs.getInt("id_sensor"));
            sensor.setIdComunidade(rs.getInt("id_comunidade"));
            sensor.setIdTipoFonte(rs.getInt("id_tipo_fonte"));
            sensor.setTipoSensor(rs.getString("tipo_sensor"));
        }
        
        stmt.close();
        return sensor;
    }
}
