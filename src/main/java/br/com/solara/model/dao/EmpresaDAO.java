package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.connection.ConnectionFactory;
import br.com.solara.model.vo.Empresa;

public class EmpresaDAO {

    private Connection minhaConexao;

    // Construtor
    public EmpresaDAO() throws ClassNotFoundException, SQLException {
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Inserir (Create)
    public String inserir(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO tb_empresas (razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, empresa.getRazaoSocialEmpresa()); // Ajustado para razaoSocialEmpresa
        stmt.setString(2, empresa.getCnpjEmpresa());
        stmt.setString(3, empresa.getSenhaEmpresa());
        stmt.setString(4, empresa.getImagemEmpresa());
        stmt.setString(5, empresa.getDescricaoEmpresa());
        stmt.executeUpdate();

        // Recupera o ID gerado
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            empresa.setIdEmpresa(rs.getInt(1));
        }

        stmt.close();
        return "Empresa cadastrada com sucesso!";
    }

    // Atualizar (Update)
    public String atualizar(Empresa empresa) throws SQLException {
        String sql = "UPDATE tb_empresas SET razao_social_empresa = ?, cnpj_empresa = ?, senha_empresa = ?, imagem_empresa = ?, descricao_empresa = ? WHERE id_empresa = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setString(1, empresa.getRazaoSocialEmpresa()); // Ajustado para razaoSocialEmpresa
        stmt.setString(2, empresa.getCnpjEmpresa());
        stmt.setString(3, empresa.getSenhaEmpresa());
        stmt.setString(4, empresa.getImagemEmpresa());
        stmt.setString(5, empresa.getDescricaoEmpresa());
        stmt.setInt(6, empresa.getIdEmpresa());
        stmt.executeUpdate();
        stmt.close();
        return "Empresa atualizada com sucesso!";
    }

    // Deletar (Delete)
    public String deletar(int idEmpresa) throws SQLException {
        String sql = "DELETE FROM tb_empresas WHERE id_empresa = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setInt(1, idEmpresa);
        stmt.executeUpdate();
        stmt.close();
        return "Empresa deletada com sucesso!";
    }

    // Consultar todas (Select All)
    public List<Empresa> selecionar() throws SQLException {
        List<Empresa> listaEmpresas = new ArrayList<>();
        String sql = "SELECT id_empresa, razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa FROM tb_empresas";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Empresa empresa = new Empresa();
            empresa.setIdEmpresa(rs.getInt("id_empresa"));
            empresa.setRazaoSocialEmpresa(rs.getString("razao_social_empresa")); // Ajustado para razaoSocialEmpresa
            empresa.setCnpjEmpresa(rs.getString("cnpj_empresa"));
            empresa.setSenhaEmpresa(rs.getString("senha_empresa"));
            empresa.setImagemEmpresa(rs.getString("imagem_empresa"));
            empresa.setDescricaoEmpresa(rs.getString("descricao_empresa"));
            listaEmpresas.add(empresa);
        }

        stmt.close();
        return listaEmpresas;
    }

    // Consultar por ID (Select by ID)
    public Empresa buscarPorId(int idEmpresa) throws SQLException {
        String sql = "SELECT id_empresa, razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa FROM tb_empresas WHERE id_empresa = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setInt(1, idEmpresa);
        ResultSet rs = stmt.executeQuery();

        Empresa empresa = null;
        if (rs.next()) {
            empresa = new Empresa();
            empresa.setIdEmpresa(rs.getInt("id_empresa"));
            empresa.setRazaoSocialEmpresa(rs.getString("razao_social_empresa")); // Ajustado para razaoSocialEmpresa
            empresa.setCnpjEmpresa(rs.getString("cnpj_empresa"));
            empresa.setSenhaEmpresa(rs.getString("senha_empresa"));
            empresa.setImagemEmpresa(rs.getString("imagem_empresa"));
            empresa.setDescricaoEmpresa(rs.getString("descricao_empresa"));
        }

        stmt.close();
        return empresa;
    }
    
    public Empresa buscarPorCnpj(String cnpj) throws SQLException {
        String sql = "SELECT id_empresa, razao_social_empresa, cnpj_empresa, senha_empresa, imagem_empresa, descricao_empresa FROM tb_empresas WHERE cnpj_empresa = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setString(1, cnpj);
        ResultSet rs = stmt.executeQuery();

        Empresa empresa = null;
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
