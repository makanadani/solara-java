package br.com.solara.model.dao;

import br.com.solara.model.vo.RegiaoSustentavel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegiaoSustentavelDAO {

    private final Connection connection;

    // Construtor
    public RegiaoSustentavelDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir uma nova região sustentável)
    public void inserir(RegiaoSustentavel regiao) throws SQLException {
        String sql = "INSERT INTO tb_regioes_sustentaveis (nome_regiao) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, regiao.getNomeRegiao());
            stmt.executeUpdate();

            // Recupera a chave gerada
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    regiao.setIdRegiao(rs.getInt(1));
                }
            }
        }
    }

    // Read (Buscar uma região sustentável pelo ID)
    public RegiaoSustentavel buscarPorId(int idRegiao) throws SQLException {
        String sql = "SELECT id_regiao, nome_regiao FROM tb_regioes_sustentaveis WHERE id_regiao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRegiao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new RegiaoSustentavel(
                        rs.getInt("id_regiao"),
                        rs.getString("nome_regiao")
                    );
                }
            }
        }
        return null; // Retorna null se a região não for encontrada
    }

    // Read (Listar todas as regiões sustentáveis)
    public List<RegiaoSustentavel> listarTodas() throws SQLException {
        List<RegiaoSustentavel> regioes = new ArrayList<>();
        String sql = "SELECT id_regiao, nome_regiao FROM tb_regioes_sustentaveis";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                regioes.add(new RegiaoSustentavel(
                    rs.getInt("id_regiao"),
                    rs.getString("nome_regiao")
                ));
            }
        }
        return regioes;
    }

    // Update (Atualizar dados de uma região sustentável)
    public void atualizar(RegiaoSustentavel regiao) throws SQLException {
        String sql = "UPDATE tb_regioes_sustentaveis SET nome_regiao = ? WHERE id_regiao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, regiao.getNomeRegiao());
            stmt.setInt(2, regiao.getIdRegiao());
            stmt.executeUpdate();
        }
    }

    // Delete (Remover uma região sustentável pelo ID)
    public void deletar(int idRegiao) throws SQLException {
        String sql = "DELETE FROM tb_regioes_sustentaveis WHERE id_regiao = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRegiao);
            stmt.executeUpdate();
        }
    }
}
