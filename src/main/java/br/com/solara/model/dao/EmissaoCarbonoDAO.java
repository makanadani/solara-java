package br.com.solara.model.dao;

import br.com.solara.model.vo.EmissaoCarbono;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmissaoCarbonoDAO {

    private final Connection connection;

    // Construtor
    public EmissaoCarbonoDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir uma nova emissão de carbono)
    public void inserir(EmissaoCarbono emissaoCarbono) throws SQLException {
        String sql = "INSERT INTO tb_emissoes_carbono (id_tipo_fonte, emissao) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, emissaoCarbono.getIdTipoFonte());
            stmt.setDouble(2, emissaoCarbono.getEmissao());
            stmt.executeUpdate();

            // Recupera a chave gerada
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    emissaoCarbono.setIdEmissao(rs.getInt(1));
                }
            }
        }
    }

    // Read (Buscar uma emissão pelo ID)
    public EmissaoCarbono buscarPorId(int idEmissao) throws SQLException {
        String sql = "SELECT id_emissao, id_tipo_fonte, emissao FROM tb_emissoes_carbono WHERE id_emissao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEmissao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new EmissaoCarbono(
                        rs.getInt("id_emissao"),
                        rs.getInt("id_tipo_fonte"),
                        rs.getDouble("emissao")
                    );
                }
            }
        }
        return null; // Retorna null se a emissão não for encontrada
    }

    // Read (Listar todas as emissões de carbono)
    public List<EmissaoCarbono> listarTodas() throws SQLException {
        List<EmissaoCarbono> emissoes = new ArrayList<>();
        String sql = "SELECT id_emissao, id_tipo_fonte, emissao FROM tb_emissoes_carbono";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                emissoes.add(new EmissaoCarbono(
                    rs.getInt("id_emissao"),
                    rs.getInt("id_tipo_fonte"),
                    rs.getDouble("emissao")
                ));
            }
        }
        return emissoes;
    }

    // Update (Atualizar dados de uma emissão de carbono)
    public void atualizar(EmissaoCarbono emissaoCarbono) throws SQLException {
        String sql = "UPDATE tb_emissoes_carbono SET id_tipo_fonte = ?, emissao = ? WHERE id_emissao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, emissaoCarbono.getIdTipoFonte());
            stmt.setDouble(2, emissaoCarbono.getEmissao());
            stmt.setInt(3, emissaoCarbono.getIdEmissao());
            stmt.executeUpdate();
        }
    }

    // Delete (Remover uma emissão pelo ID)
    public void deletar(int idEmissao) throws SQLException {
        String sql = "DELETE FROM tb_emissoes_carbono WHERE id_emissao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEmissao);
            stmt.executeUpdate();
        }
    }
}
