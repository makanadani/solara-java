package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.connection.ConnectionFactory;
import br.com.solara.model.vo.ProjetoSustentavel;

// Classe para métodos CRUD dos projetos sustentáveis
public class ProjetoSustentavelDAO {

    private Connection minhaConexao;

    public ProjetoSustentavelDAO() throws ClassNotFoundException, SQLException {
        super();
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(ProjetoSustentavel projeto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO tb_projetos_sustentaveis (id_tipo_fonte, id_regiao, descricao_projeto, custo_projeto, status_projeto) VALUES (?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setObject(1, projeto.getIdTipoFonte(), Types.INTEGER);
        stmt.setObject(2, projeto.getIdRegiao(), Types.INTEGER);
        stmt.setString(3, projeto.getDescricaoProjeto());
        stmt.setDouble(4, projeto.getCustoProjeto());
        stmt.setString(5, projeto.getStatusProjeto());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            projeto.setIdProjeto(rs.getInt(1));
        }

        stmt.close();
        return "Cadastrado com sucesso!";
    }

    // Update
    public String atualizar(ProjetoSustentavel projeto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE tb_projetos_sustentaveis SET id_tipo_fonte = ?, id_regiao = ?, descricao_projeto = ?, custo_projeto = ?, status_projeto = ? WHERE id_projeto = ?");
        stmt.setObject(1, projeto.getIdTipoFonte(), Types.INTEGER);
        stmt.setObject(2, projeto.getIdRegiao(), Types.INTEGER);
        stmt.setString(3, projeto.getDescricaoProjeto());
        stmt.setDouble(4, projeto.getCustoProjeto());
        stmt.setString(5, projeto.getStatusProjeto());
        stmt.setInt(6, projeto.getIdProjeto());
        stmt.executeUpdate();
        stmt.close();
        return "Atualizado com sucesso!";
    }

    // Delete
    public String deletar(int idProjeto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_projetos_sustentaveis WHERE id_projeto = ?");
        stmt.setInt(1, idProjeto);
        stmt.executeUpdate();
        stmt.close();
        return "Deletado com sucesso!";
    }

    // Select All
    public List<ProjetoSustentavel> selecionarTodos() throws SQLException {
        List<ProjetoSustentavel> listaProjetos = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_projeto, id_tipo_fonte, id_regiao, descricao_projeto, custo_projeto, status_projeto FROM tb_projetos_sustentaveis");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ProjetoSustentavel projeto = new ProjetoSustentavel();
            projeto.setIdProjeto(rs.getInt("id_projeto"));
            projeto.setIdTipoFonte(rs.getObject("id_tipo_fonte") != null ? rs.getInt("id_tipo_fonte") : null);
            projeto.setIdRegiao(rs.getObject("id_regiao") != null ? rs.getInt("id_regiao") : null);
            projeto.setDescricaoProjeto(rs.getString("descricao_projeto"));
            projeto.setCustoProjeto(rs.getDouble("custo_projeto"));
            projeto.setStatusProjeto(rs.getString("status_projeto"));
            listaProjetos.add(projeto);
        }

        stmt.close();
        return listaProjetos;
    }

    // Select
    public ProjetoSustentavel selecionar(int idProjeto) throws SQLException {
        ProjetoSustentavel projeto = null;
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_projeto, id_tipo_fonte, id_regiao, descricao_projeto, custo_projeto, status_projeto FROM tb_projetos_sustentaveis WHERE id_projeto = ?");
        stmt.setInt(1, idProjeto);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            projeto = new ProjetoSustentavel();
            projeto.setIdProjeto(rs.getInt("id_projeto"));
            projeto.setIdTipoFonte(rs.getObject("id_tipo_fonte") != null ? rs.getInt("id_tipo_fonte") : null);
            projeto.setIdRegiao(rs.getObject("id_regiao") != null ? rs.getInt("id_regiao") : null);
            projeto.setDescricaoProjeto(rs.getString("descricao_projeto"));
            projeto.setCustoProjeto(rs.getDouble("custo_projeto"));
            projeto.setStatusProjeto(rs.getString("status_projeto"));
        }

        stmt.close();
        return projeto;
    }
}
