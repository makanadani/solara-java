package br.com.solara.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.connection.ConnectionFactory;
import br.com.solara.model.vo.Comunidade;

// Classe para métodos CRUD das comunidades
public class ComunidadeDAO {

    public Connection minhaConexao;

    public ComunidadeDAO() throws ClassNotFoundException, SQLException {
        this.minhaConexao = new ConnectionFactory().conexao();
    }

    // Insert
    public String inserir(Comunidade comunidade) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO tb_comunidades (id_empresa, id_regiao, protocolo_atendimento_comunidade, " +
                        "nome_comunidade, latitude_comunidade, longitude_comunidade) VALUES (?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, comunidade.getIdEmpresa());
        stmt.setInt(2, comunidade.getIdRegiao());
        stmt.setObject(3, comunidade.getProtocoloAtendimentoComunidade(), java.sql.Types.INTEGER);
        stmt.setString(4, comunidade.getNomeComunidade());
        stmt.setDouble(5, comunidade.getLatitudeComunidade());
        stmt.setDouble(6, comunidade.getLongitudeComunidade());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            comunidade.setIdComunidade(rs.getInt(1));
        }

        stmt.close();
        return "Comunidade cadastrada com sucesso!";
    }

    // Delete
    public String deletar(int idComunidade) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM tb_comunidades WHERE id_comunidade = ?");
        stmt.setInt(1, idComunidade);
        stmt.execute();
        stmt.close();
        return "Comunidade deletada com sucesso!";
    }

    // Update
    public String atualizar(Comunidade comunidade) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE tb_comunidades SET id_empresa = ?, id_regiao = ?, protocolo_atendimento_comunidade = ?, " +
                        "nome_comunidade = ?, latitude_comunidade = ?, longitude_comunidade = ? WHERE id_comunidade = ?");

        stmt.setInt(1, comunidade.getIdEmpresa());
        stmt.setInt(2, comunidade.getIdRegiao());
        stmt.setObject(3, comunidade.getProtocoloAtendimentoComunidade(), java.sql.Types.INTEGER);
        stmt.setString(4, comunidade.getNomeComunidade());
        stmt.setDouble(5, comunidade.getLatitudeComunidade());
        stmt.setDouble(6, comunidade.getLongitudeComunidade());
        stmt.setInt(7, comunidade.getIdComunidade());
        stmt.executeUpdate();
        stmt.close();
        return "Comunidade atualizada com sucesso!";
    }

    // Select All
    public List<Comunidade> selecionarTodos() throws SQLException {
        List<Comunidade> listaComunidades = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_comunidade, id_empresa, id_regiao, protocolo_atendimento_comunidade, " +
                        "nome_comunidade, latitude_comunidade, longitude_comunidade FROM tb_comunidades");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Comunidade comunidade = new Comunidade();
            comunidade.setIdComunidade(rs.getInt("id_comunidade"));
            comunidade.setIdEmpresa(rs.getInt("id_empresa"));
            comunidade.setIdRegiao(rs.getInt("id_regiao"));
            comunidade.setProtocoloAtendimentoComunidade(
                    rs.getObject("protocolo_atendimento_comunidade") != null ?
                            rs.getInt("protocolo_atendimento_comunidade") : null);
            comunidade.setNomeComunidade(rs.getString("nome_comunidade"));
            comunidade.setLatitudeComunidade(rs.getDouble("latitude_comunidade"));
            comunidade.setLongitudeComunidade(rs.getDouble("longitude_comunidade"));
            listaComunidades.add(comunidade);
        }

        stmt.close();
        return listaComunidades;
    }

    // Select
    public Comunidade selecionar(int idComunidade) throws SQLException {
        Comunidade comunidade = null;
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT id_comunidade, id_empresa, id_regiao, protocolo_atendimento_comunidade, " +
                        "nome_comunidade, latitude_comunidade, longitude_comunidade " +
                        "FROM tb_comunidades WHERE id_comunidade = ?");
        stmt.setInt(1, idComunidade);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            comunidade = new Comunidade();
            comunidade.setIdComunidade(rs.getInt("id_comunidade"));
            comunidade.setIdEmpresa(rs.getInt("id_empresa"));
            comunidade.setIdRegiao(rs.getInt("id_regiao"));
            comunidade.setProtocoloAtendimentoComunidade(
                    rs.getObject("protocolo_atendimento_comunidade") != null ?
                            rs.getInt("protocolo_atendimento_comunidade") : null);
            comunidade.setNomeComunidade(rs.getString("nome_comunidade"));
            comunidade.setLatitudeComunidade(rs.getDouble("latitude_comunidade"));
            comunidade.setLongitudeComunidade(rs.getDouble("longitude_comunidade"));
        }

        stmt.close();
        return comunidade;
    }
}
