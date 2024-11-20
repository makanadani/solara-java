package br.com.solara.model.dao;

import br.com.solara.model.vo.TipoFonte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoFonteDAO {

    private final Connection connection;

    // Construtor
    public TipoFonteDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir um novo tipo de fonte)
    public void inserir(TipoFonte tipoFonte) throws SQLException {
        String sql = "INSERT INTO tb_tipo_fontes (nome_fonte) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipoFonte.getNomeFonte());
            stmt.executeUpdate();

            // Recupera a chave gerada
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tipoFonte.setIdTipoFonte(rs.getInt(1));
                }
            }
        }
    }

    // Read (Buscar um tipo de fonte pelo ID)
    public TipoFonte buscarPorId(int idTipoFonte) throws SQLException {
        String sql = "SELECT id_tipo_fonte, nome_fonte FROM tb_tipo_fontes WHERE id_tipo_fonte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoFonte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoFonte(
                        rs.getInt("id_tipo_fonte"),
                        rs.getString("nome_fonte")
                    );
                }
            }
        }
        return null; // Retorna null se o tipo de fonte não for encontrado
    }

    // Read (Listar todos os tipos de fonte)
    public List<TipoFonte> listarTodos() throws SQLException {
        List<TipoFonte> tiposFonte = new ArrayList<>();
        String sql = "SELECT id_tipo_fonte, nome_fonte FROM tb_tipo_fontes";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tiposFonte.add(new TipoFonte(
                    rs.getInt("id_tipo_fonte"),
                    rs.getString("nome_fonte")
                ));
            }
        }
        return tiposFonte;
    }

    // Update (Atualizar um tipo de fonte)
    public void atualizar(TipoFonte tipoFonte) throws SQLException {
        String sql = "UPDATE tb_tipo_fontes SET nome_fonte = ? WHERE id_tipo_fonte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoFonte.getNomeFonte());
            stmt.setInt(2, tipoFonte.getIdTipoFonte());
            stmt.executeUpdate();
        }
    }

    // Delete (Remover um tipo de fonte pelo ID)
    public void deletar(int idTipoFonte) throws SQLException {
        String sql = "DELETE FROM tb_tipo_fontes WHERE id_tipo_fonte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoFonte);
            stmt.executeUpdate();
        }
    }
}
