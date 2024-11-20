package br.com.solara.model.dao;

import br.com.solara.model.vo.ComunidadeProjeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComunidadeProjetoDAO {

    private final Connection connection;

    // Construtor
    public ComunidadeProjetoDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir uma nova relação comunidade-projeto)
    public void inserir(ComunidadeProjeto comunidadeProjeto) throws SQLException {
        String sql = "INSERT INTO tb_comunidades_projetos (id_comunidade, id_projeto) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comunidadeProjeto.getIdComunidade());
            stmt.setInt(2, comunidadeProjeto.getIdProjeto());
            stmt.executeUpdate();
        }
    }

    // Read (Listar todas as relações)
    public List<ComunidadeProjeto> listarTodas() throws SQLException {
        List<ComunidadeProjeto> lista = new ArrayList<>();
        String sql = "SELECT id_comunidade, id_projeto FROM tb_comunidades_projetos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new ComunidadeProjeto(
                        rs.getInt("id_comunidade"),
                        rs.getInt("id_projeto")
                ));
            }
        }
        return lista;
    }

    // Read (Buscar relações por ID da comunidade)
    public List<ComunidadeProjeto> buscarPorIdComunidade(int idComunidade) throws SQLException {
        List<ComunidadeProjeto> lista = new ArrayList<>();
        String sql = "SELECT id_comunidade, id_projeto FROM tb_comunidades_projetos WHERE id_comunidade = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idComunidade);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ComunidadeProjeto(
                            rs.getInt("id_comunidade"),
                            rs.getInt("id_projeto")
                    ));
                }
            }
        }
        return lista;
    }

    // Read (Buscar relações por ID do projeto)
    public List<ComunidadeProjeto> buscarPorIdProjeto(int idProjeto) throws SQLException {
        List<ComunidadeProjeto> lista = new ArrayList<>();
        String sql = "SELECT id_comunidade, id_projeto FROM tb_comunidades_projetos WHERE id_projeto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ComunidadeProjeto(
                            rs.getInt("id_comunidade"),
                            rs.getInt("id_projeto")
                    ));
                }
            }
        }
        return lista;
    }

    // Delete (Remover uma relação por ID da comunidade e do projeto)
    public void deletar(int idComunidade, int idProjeto) throws SQLException {
        String sql = "DELETE FROM tb_comunidades_projetos WHERE id_comunidade = ? AND id_projeto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idComunidade);
            stmt.setInt(2, idProjeto);
            stmt.executeUpdate();
        }
    }

    // Delete (Remover todas as relações de uma comunidade)
    public void deletarPorIdComunidade(int idComunidade) throws SQLException {
        String sql = "DELETE FROM tb_comunidades_projetos WHERE id_comunidade = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idComunidade);
            stmt.executeUpdate();
        }
    }

    // Delete (Remover todas as relações de um projeto)
    public void deletarPorIdProjeto(int idProjeto) throws SQLException {
        String sql = "DELETE FROM tb_comunidades_projetos WHERE id_projeto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            stmt.executeUpdate();
        }
    }
}
