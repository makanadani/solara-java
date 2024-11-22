package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.model.vo.RegiaoSustentavel;
import br.com.solara.connection.ConnectionFactory;

// Classe para métodos CRUD das regiões sustentáveis
public class RegiaoSustentavelDAO {

    public Connection minhaConexao;

    public RegiaoSustentavelDAO() throws ClassNotFoundException, SQLException {
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(RegiaoSustentavel regiao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO tb_regioes_sustentaveis (nome_regiao) VALUES (?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        
        stmt.setString(1, regiao.getNomeRegiao());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            regiao.setIdRegiao(rs.getInt(1));
        }

        stmt.close();
        return "Região sustentável cadastrada com sucesso!";
    }

    // Delete
    public String deletar(int idRegiao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_regioes_sustentaveis WHERE id_regiao = ?");
        
        stmt.setInt(1, idRegiao);
        stmt.execute();
        stmt.close();
        return "Região sustentável deletada com sucesso!";
    }

    // Update
    public String atualizar(RegiaoSustentavel regiao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE tb_regioes_sustentaveis SET nome_regiao = ? WHERE id_regiao = ?");
        
        stmt.setString(1, regiao.getNomeRegiao());
        stmt.setInt(2, regiao.getIdRegiao());
        stmt.executeUpdate();
        stmt.close();
        return "Região sustentável atualizada com sucesso!";
    }

    // Select All
    public List<RegiaoSustentavel> selecionarTodos() throws SQLException {
        List<RegiaoSustentavel> listaRegioes = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_regiao, nome_regiao FROM tb_regioes_sustentaveis");
        
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            RegiaoSustentavel regiao = new RegiaoSustentavel();
            regiao.setIdRegiao(rs.getInt("id_regiao"));
            regiao.setNomeRegiao(rs.getString("nome_regiao"));
            listaRegioes.add(regiao);
        }

        stmt.close();
        return listaRegioes;
    }

    // Select
    public RegiaoSustentavel selecionar(int idRegiao) throws SQLException {
        RegiaoSustentavel regiao = null;
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_regiao, nome_regiao FROM tb_regioes_sustentaveis WHERE id_regiao = ?");
        
        stmt.setInt(1, idRegiao);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            regiao = new RegiaoSustentavel();
            regiao.setIdRegiao(rs.getInt("id_regiao"));
            regiao.setNomeRegiao(rs.getString("nome_regiao"));
        }

        stmt.close();
        return regiao;
    }
}
