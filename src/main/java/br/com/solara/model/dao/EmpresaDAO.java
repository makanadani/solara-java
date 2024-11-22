package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.connection.ConnectionFactory;
import br.com.solara.model.vo.Empresa;

// Classe para métodos CRUD das empresas que gerenciam microgrids
public class EmpresaDAO {

    private Connection minhaConexao;

    public EmpresaDAO() throws ClassNotFoundException, SQLException {
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(Empresa empresa) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO tb_empresas (razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa) VALUES (?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, empresa.getRazaoSocialEmpresa());
        stmt.setString(2, empresa.getCnpjEmpresa());
        stmt.setString(3, empresa.getSenhaEmpresa());
        stmt.setString(4, empresa.getImagemEmpresa());
        stmt.setString(5, empresa.getDescricaoEmpresa());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            empresa.setIdEmpresa(rs.getInt(1));
        }

        stmt.close();
        return "Cadastrado com sucesso!";
    }

    // Update
    public String atualizar(Empresa empresa) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE tb_empresas SET razao_social_empresa = ?, cnpj_empresa = ?, senha_empresa = ?, imagem_empresa = ?, descricao_empresa = ? WHERE id_empresa = ?");
        stmt.setString(1, empresa.getRazaoSocialEmpresa());
        stmt.setString(2, empresa.getCnpjEmpresa());
        stmt.setString(3, empresa.getSenhaEmpresa());
        stmt.setString(4, empresa.getImagemEmpresa());
        stmt.setString(5, empresa.getDescricaoEmpresa());
        stmt.setInt(6, empresa.getIdEmpresa());
        stmt.executeUpdate();
        stmt.close();
        return "Atualizado com sucesso!";
    }

    // Delete
    public String deletar(int idEmpresa) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement("DELETE FROM tb_empresas WHERE id_empresa = ?");
        stmt.setInt(1, idEmpresa);
        stmt.executeUpdate();
        stmt.close();
        return "Deletado com sucesso!";
    }

    // Select All
    public List<Empresa> selecionarTodos() throws SQLException {
        List<Empresa> listaEmpresas = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_empresa, razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa FROM tb_empresas");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Empresa empresa = new Empresa();
            empresa.setIdEmpresa(rs.getInt("id_empresa"));
            empresa.setRazaoSocialEmpresa(rs.getString("razao_social_empresa"));
            empresa.setCnpjEmpresa(rs.getString("cnpj_empresa"));
            empresa.setSenhaEmpresa(rs.getString("senha_empresa"));
            empresa.setImagemEmpresa(rs.getString("imagem_empresa"));
            empresa.setDescricaoEmpresa(rs.getString("descricao_empresa"));
            listaEmpresas.add(empresa);
        }

        stmt.close();
        return listaEmpresas;
    }

    // Select ID
    public Empresa selecionarPorId(int idEmpresa) throws SQLException {
        Empresa empresa = null;
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_empresa, razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa FROM tb_empresas WHERE id_empresa = ?");
        stmt.setInt(1, idEmpresa);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            empresa = new Empresa();
            empresa.setIdEmpresa(rs.getInt("id_empresa"));
            empresa.setRazaoSocialEmpresa(rs.getString("razao_social_empresa"));
            empresa.setCnpjEmpresa(rs.getString("cnpj_empresa"));
            empresa.setSenhaEmpresa(rs.getString("senha_empresa"));
            empresa.setImagemEmpresa(rs.getString("imagem_empresa"));
            empresa.setDescricaoEmpresa(rs.getString("descricao_empresa"));
        }

        stmt.close();
        return empresa;
    }

    // Select CNPJ
    public Empresa selecionarPorCnpj(String cnpj) throws SQLException {
        Empresa empresa = null;
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_empresa, razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa FROM tb_empresas WHERE cnpj_empresa = ?");
        stmt.setString(1, cnpj);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            empresa = new Empresa();
            empresa.setIdEmpresa(rs.getInt("id_empresa"));
            empresa.setRazaoSocialEmpresa(rs.getString("razao_social_empresa"));
            empresa.setCnpjEmpresa(rs.getString("cnpj_empresa"));
            empresa.setSenhaEmpresa(rs.getString("senha_empresa"));
            empresa.setImagemEmpresa(rs.getString("imagem_empresa"));
            empresa.setDescricaoEmpresa(rs.getString("descricao_empresa"));
        }

        stmt.close();
        return empresa;
    }
}
