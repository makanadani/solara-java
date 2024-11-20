package br.com.solara.model.dao;

import br.com.solara.model.vo.Comunidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComunidadeDAO {

    private final Connection connection;

    // Construtor
    public ComunidadeDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir uma nova comunidade)
    public void inserir(Comunidade comunidade) throws SQLException {
        String sql = "INSERT INTO tb_comunidades (id_empresa, id_regiao, protocolo_atendimento_comunidade, " +
                     "nome_comunidade, latitude_comunidade, longitude_comunidade) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, comunidade.getIdEmpresa());
            stmt.setInt(2, comunidade.getIdRegiao());
            stmt.setObject(3, comunidade.getProtocoloAtendimentoComunidade(), Types.INTEGER);
            stmt.setString(4, comunidade.getNomeComunidade());
            stmt.setDouble(5, comunidade.getLatitudeComunidade());
            stmt.setDouble(6, comunidade.getLongitudeComunidade());
            stmt.executeUpdate();

            // Recupera a chave gerada
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    comunidade.setIdComunidade(rs.getInt(1));
                }
            }
        }
    }

    // Read (Buscar uma comunidade pelo ID)
    public Comunidade buscarPorId(int idComunidade) throws SQLException {
        String sql = "SELECT id_comunidade, id_empresa, id_regiao, protocolo_atendimento_comunidade, " +
                     "nome_comunidade, latitude_comunidade, longitude_comunidade " +
                     "FROM tb_comunidades WHERE id_comunidade = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idComunidade);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Comunidade(
                        rs.getInt("id_comunidade"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_regiao"),
                        rs.getObject("protocolo_atendimento_comunidade") != null ?
                                rs.getInt("protocolo_atendimento_comunidade") : null,
                        rs.getString("nome_comunidade"),
                        rs.getDouble("latitude_comunidade"),
                        rs.getDouble("longitude_comunidade")
                    );
                }
            }
        }
        return null;
    }

    // Read (Listar todas as comunidades)
    public List<Comunidade> listarTodas() throws SQLException {
        List<Comunidade> comunidades = new ArrayList<>();
        String sql = "SELECT id_comunidade, id_empresa, id_regiao, protocolo_atendimento_comunidade, " +
                     "nome_comunidade, latitude_comunidade, longitude_comunidade FROM tb_comunidades";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                comunidades.add(new Comunidade(
                    rs.getInt("id_comunidade"),
                    rs.getInt("id_empresa"),
                    rs.getInt("id_regiao"),
                    rs.getObject("protocolo_atendimento_comunidade") != null ?
                            rs.getInt("protocolo_atendimento_comunidade") : null,
                    rs.getString("nome_comunidade"),
                    rs.getDouble("latitude_comunidade"),
                    rs.getDouble("longitude_comunidade")
                ));
            }
        }
        return comunidades;
    }

    // Update (Atualizar dados de uma comunidade)
    public void atualizar(Comunidade comunidade) throws SQLException {
        String sql = "UPDATE tb_comunidades SET id_empresa = ?, id_regiao = ?, protocolo_atendimento_comunidade = ?, " +
                     "nome_comunidade = ?, latitude_comunidade = ?, longitude_comunidade = ? WHERE id_comunidade = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comunidade.getIdEmpresa());
            stmt.setInt(2, comunidade.getIdRegiao());
            stmt.setObject(3, comunidade.getProtocoloAtendimentoComunidade(), Types.INTEGER);
            stmt.setString(4, comunidade.getNomeComunidade());
            stmt.setDouble(5, comunidade.getLatitudeComunidade());
            stmt.setDouble(6, comunidade.getLongitudeComunidade());
            stmt.setInt(7, comunidade.getIdComunidade());
            stmt.executeUpdate();
        }
    }

    // Delete (Remover uma comunidade pelo ID)
    public void deletar(int idComunidade) throws SQLException {
        String sql = "DELETE FROM tb_comunidades WHERE id_comunidade = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idComunidade);
            stmt.executeUpdate();
        }
    }
}
