package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.connection.ConnectionFactory;
import br.com.solara.model.vo.ComunidadeProjeto;

// Classe para métodos CRUD das associações entre comunidades e projetos
public class ComunidadeProjetoDAO {

    public Connection minhaConexao;

    public ComunidadeProjetoDAO() throws ClassNotFoundException, SQLException {
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(ComunidadeProjeto comunidadeProjeto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO tb_comunidades_projetos (id_comunidade, id_projeto) VALUES (?, ?)");
        stmt.setInt(1, comunidadeProjeto.getIdComunidade());
        stmt.setInt(2, comunidadeProjeto.getIdProjeto());
        stmt.executeUpdate();
        stmt.close();
        return "Relação comunidade-projeto cadastrada com sucesso!";
    }

    // Delete
    public String deletar(int idComunidade, int idProjeto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_comunidades_projetos WHERE id_comunidade = ? AND id_projeto = ?");
        stmt.setInt(1, idComunidade);
        stmt.setInt(2, idProjeto);
        stmt.executeUpdate();
        stmt.close();
        return "Relação comunidade-projeto deletada com sucesso!";
    }

    // Delete por ID da Comunidade
    public String deletarPorIdComunidade(int idComunidade) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_comunidades_projetos WHERE id_comunidade = ?");
        stmt.setInt(1, idComunidade);
        stmt.executeUpdate();
        stmt.close();
        return "Todas as relações da comunidade deletadas com sucesso!";
    }

    // Delete por ID do Projeto
    public String deletarPorIdProjeto(int idProjeto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_comunidades_projetos WHERE id_projeto = ?");
        stmt.setInt(1, idProjeto);
        stmt.executeUpdate();
        stmt.close();
        return "Todas as relações do projeto deletadas com sucesso!";
    }

    // Select All
    public List<ComunidadeProjeto> selecionarTodos() throws SQLException {
        List<ComunidadeProjeto> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_comunidade, id_projeto FROM tb_comunidades_projetos");
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ComunidadeProjeto comunidadeProjeto = new ComunidadeProjeto();
            comunidadeProjeto.setIdComunidade(rs.getInt("id_comunidade"));
            comunidadeProjeto.setIdProjeto(rs.getInt("id_projeto"));
            lista.add(comunidadeProjeto);
        }
        stmt.close();
        return lista;
    }

    // Select por ID da Comunidade
    public List<String> selecionarPorIdComunidade(int idComunidade) throws SQLException {
        List<String> descricoes = new ArrayList<>();
        String sql = "SELECT p.descricao_projeto " +
                     "FROM tb_comunidades_projetos cp " +
                     "JOIN tb_projetos_sustentaveis p ON cp.id_projeto = p.id_projeto " +
                     "WHERE cp.id_comunidade = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setInt(1, idComunidade);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            descricoes.add(rs.getString("descricao_projeto"));
        }
        stmt.close();
        return descricoes;
    }

    // Select por ID do Projeto
    public List<ComunidadeProjeto> selecionarPorIdProjeto(int idProjeto) throws SQLException {
        List<ComunidadeProjeto> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_comunidade, id_projeto FROM tb_comunidades_projetos WHERE id_projeto = ?");
        stmt.setInt(1, idProjeto);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ComunidadeProjeto comunidadeProjeto = new ComunidadeProjeto();
            comunidadeProjeto.setIdComunidade(rs.getInt("id_comunidade"));
            comunidadeProjeto.setIdProjeto(rs.getInt("id_projeto"));
            lista.add(comunidadeProjeto);
        }
        stmt.close();
        return lista;
    }
}
