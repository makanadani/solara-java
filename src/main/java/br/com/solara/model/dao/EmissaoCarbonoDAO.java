package br.com.solara.model.dao;

import br.com.solara.model.vo.EmissaoCarbono;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe para métodos CRUD das emissões de carbono
public class EmissaoCarbonoDAO {

    private final Connection minhaConexao;

    public EmissaoCarbonoDAO(Connection connection) {
        this.minhaConexao = connection;
    }

    // Insert
    public String inserir(EmissaoCarbono emissao) throws SQLException {
        String sql = "INSERT INTO tb_emissoes_carbono (id_tipo_fonte, emissao) VALUES (?, ?)";
        try (PreparedStatement stmt = minhaConexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, emissao.getIdTipoFonte());
            stmt.setDouble(2, emissao.getEmissao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    emissao.setIdEmissao(rs.getInt(1));
                }
            }
        }
        return "Emissão de carbono cadastrada com sucesso!";
    }

    // Update
    public String atualizar(EmissaoCarbono emissao) throws SQLException {
        String sql = "UPDATE tb_emissoes_carbono SET id_tipo_fonte = ?, emissao = ? WHERE id_emissao = ?";
        try (PreparedStatement stmt = minhaConexao.prepareStatement(sql)) {
            stmt.setInt(1, emissao.getIdTipoFonte());
            stmt.setDouble(2, emissao.getEmissao());
            stmt.setInt(3, emissao.getIdEmissao());
            stmt.executeUpdate();
        }
        return "Emissão de carbono atualizada com sucesso!";
    }

    // Delete
    public String deletar(int idEmissao) throws SQLException {
        String sql = "DELETE FROM tb_emissoes_carbono WHERE id_emissao = ?";
        try (PreparedStatement stmt = minhaConexao.prepareStatement(sql)) {
            stmt.setInt(1, idEmissao);
            stmt.executeUpdate();
        }
        return "Emissão de carbono deletada com sucesso!";
    }

    // Select
    public EmissaoCarbono selecionar(int idEmissao) throws SQLException {
        String sql = "SELECT id_emissao, id_tipo_fonte, emissao FROM tb_emissoes_carbono WHERE id_emissao = ?";
        EmissaoCarbono emissao = null;
        try (PreparedStatement stmt = minhaConexao.prepareStatement(sql)) {
            stmt.setInt(1, idEmissao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    emissao = new EmissaoCarbono();
                    emissao.setIdEmissao(rs.getInt("id_emissao"));
                    emissao.setIdTipoFonte(rs.getInt("id_tipo_fonte"));
                    emissao.setEmissao(rs.getDouble("emissao"));
                }
            }
        }
        return emissao;
    }

    // Select All
    public List<EmissaoCarbono> selecionarTodos() throws SQLException {
        List<EmissaoCarbono> emissoes = new ArrayList<>();
        String sql = "SELECT id_emissao, id_tipo_fonte, emissao FROM tb_emissoes_carbono";
        try (PreparedStatement stmt = minhaConexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EmissaoCarbono emissao = new EmissaoCarbono();
                emissao.setIdEmissao(rs.getInt("id_emissao"));
                emissao.setIdTipoFonte(rs.getInt("id_tipo_fonte"));
                emissao.setEmissao(rs.getDouble("emissao"));
                emissoes.add(emissao);
            }
        }
        return emissoes;
    }
}
