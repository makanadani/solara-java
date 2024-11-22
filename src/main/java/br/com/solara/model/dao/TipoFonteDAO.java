package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.model.vo.TipoFonte;
import br.com.solara.connection.ConnectionFactory;

// Classe para métodos CRUD dos tipos de fontes de energia
public class TipoFonteDAO {
    
    public Connection minhaConexao;

    public TipoFonteDAO() throws ClassNotFoundException, SQLException {
        super();
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(TipoFonte tipoFonte) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement
                ("INSERT INTO tb_tipo_fontes (nome_fonte) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, tipoFonte.getNomeFonte());
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            tipoFonte.setIdTipoFonte(rs.getInt(1));
        }
        
        stmt.close();
        return "Cadastrado com Sucesso!";
    }

    // Delete
    public String deletar(int idTipoFonte) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement
                ("DELETE FROM tb_tipo_fontes WHERE id_tipo_fonte = ?");
        stmt.setInt(1, idTipoFonte);
        stmt.execute();
        stmt.close();
        return "Deletado com Sucesso!";
    }

    // Update
    public String atualizar(TipoFonte tipoFonte) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement
                ("UPDATE tb_tipo_fontes SET nome_fonte = ? WHERE id_tipo_fonte = ?");
        stmt.setString(1, tipoFonte.getNomeFonte());
        stmt.setInt(2, tipoFonte.getIdTipoFonte());
        stmt.executeUpdate();
        stmt.close();
        return "Atualizado com Sucesso!";
    }

    // Select All
    public List<TipoFonte> selecionarTodos() throws SQLException {
        List<TipoFonte> listaTipoFonte = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement
                ("SELECT id_tipo_fonte, nome_fonte FROM tb_tipo_fontes");
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            TipoFonte tipoFonte = new TipoFonte();
            tipoFonte.setIdTipoFonte(rs.getInt("id_tipo_fonte"));
            tipoFonte.setNomeFonte(rs.getString("nome_fonte"));
            listaTipoFonte.add(tipoFonte);
        }
        
        stmt.close();
        return listaTipoFonte;
    }

    // Select
    public TipoFonte selecionar(int idTipoFonte) throws SQLException {
        TipoFonte tipoFonte = null;
        PreparedStatement stmt = minhaConexao.prepareStatement
                ("SELECT id_tipo_fonte, nome_fonte FROM tb_tipo_fontes WHERE id_tipo_fonte = ?");
        stmt.setInt(1, idTipoFonte);
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            tipoFonte = new TipoFonte();
            tipoFonte.setIdTipoFonte(rs.getInt("id_tipo_fonte"));
            tipoFonte.setNomeFonte(rs.getString("nome_fonte"));
        }
        
        stmt.close();
        return tipoFonte;
    }
}
