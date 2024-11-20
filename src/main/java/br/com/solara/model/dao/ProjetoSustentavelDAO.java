package br.com.solara.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.model.vo.ProjetoSustentavel;

public class ProjetoSustentavelDAO {

    private final Connection connection;

    // Construtor
    public ProjetoSustentavelDAO(Connection connection) {
        this.connection = connection;
    }

    // Create (Inserir um novo projeto sustentável)
    public void inserir(ProjetoSustentavel projeto) throws SQLException {
        String sql = "INSERT INTO tb_projetos_sustentaveis (id_tipo_fonte, id_regiao, descricao_projeto, custo_projeto, status_projeto) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setObject(1, projeto.getIdTipoFonte(), Types.INTEGER); // Pode ser null
            stmt.setObject(2, projeto.getIdRegiao(), Types.INTEGER); // Pode ser null
            stmt.setString(3, projeto.getDescricaoProjeto());
            stmt.setDouble(4, projeto.getCustoProjeto());
            stmt.setString(5, projeto.getStatusProjeto());
            stmt.executeUpdate();

            // Recupera a chave gerada
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    projeto.setIdProjeto(rs.getInt(1));
                }
            }
        }
    }

    // Read (Buscar um projeto sustentável pelo ID)
    public ProjetoSustentavel buscarPorId(int idProjeto) throws SQLException {
        String sql = "SELECT id_projeto, id_tipo_fonte, id_regiao, descricao_projeto, custo_projeto, status_projeto " +
                     "FROM tb_projetos_sustentaveis WHERE id_projeto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProjetoSustentavel(
                        rs.getInt("id_projeto"),
                        rs.getObject("id_tipo_fonte") != null ? rs.getInt("id_tipo_fonte") : null,
                        rs.getObject("id_regiao") != null ? rs.getInt("id_regiao") : null,
                        rs.getString("descricao_projeto"),
                        rs.getDouble("custo_projeto"),
                        rs.getString("status_projeto")
                    );
                }
            }
        }
        return null; // Retorna null se o projeto não for encontrado
    }

    // Read (Listar todos os projetos sustentáveis)
    public List<ProjetoSustentavel> listarTodos() throws SQLException {
        List<ProjetoSustentavel> projetos = new ArrayList<>();
        String sql = "SELECT id_projeto, id_tipo_fonte, id_regiao, descricao_projeto, custo_projeto, status_projeto " +
                     "FROM tb_projetos_sustentaveis";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                projetos.add(new ProjetoSustentavel(
                    rs.getInt("id_projeto"),
                    rs.getObject("id_tipo_fonte") != null ? rs.getInt("id_tipo_fonte") : null,
                    rs.getObject("id_regiao") != null ? rs.getInt("id_regiao") : null,
                    rs.getString("descricao_projeto"),
                    rs.getDouble("custo_projeto"),
                    rs.getString("status_projeto")
                ));
            }
        }
        return projetos;
    }

    // Update (Atualizar dados de um projeto sustentável)
    public void atualizar(ProjetoSustentavel projeto) throws SQLException {
        String sql = "UPDATE tb_projetos_sustentaveis SET id_tipo_fonte = ?, id_regiao = ?, descricao_projeto = ?, " +
                     "custo_projeto = ?, status_projeto = ? WHERE id_projeto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, projeto.getIdTipoFonte(), Types.INTEGER); // Pode ser null
            stmt.setObject(2, projeto.getIdRegiao(), Types.INTEGER); // Pode ser null
            stmt.setString(3, projeto.getDescricaoProjeto());
            stmt.setDouble(4, projeto.getCustoProjeto());
            stmt.setString(5, projeto.getStatusProjeto());
            stmt.setInt(6, projeto.getIdProjeto());
            stmt.executeUpdate();
        }
    }

    // Delete (Remover um projeto sustentável pelo ID)
    public void deletar(int idProjeto) throws SQLException {
        String sql = "DELETE FROM tb_projetos_sustentaveis WHERE id_projeto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            stmt.executeUpdate();
        }
    }
}
