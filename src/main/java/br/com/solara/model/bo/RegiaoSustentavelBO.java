package br.com.solara.model.bo;

import br.com.solara.model.dao.RegiaoSustentavelDAO;
import br.com.solara.model.vo.RegiaoSustentavel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RegiaoSustentavelBO {

    private final RegiaoSustentavelDAO regiaoSustentavelDAO;

    // Construtor
    public RegiaoSustentavelBO(Connection connection) {
        this.regiaoSustentavelDAO = new RegiaoSustentavelDAO(connection);
    }

    // Método para inserir uma nova região sustentável com validações
    public void inserirRegiao(RegiaoSustentavel regiao) throws Exception {
        validarCamposObrigatorios(regiao);

        // Insere no banco
        regiaoSustentavelDAO.inserir(regiao);
    }

    // Método para buscar uma região sustentável pelo ID
    public RegiaoSustentavel buscarPorId(int idRegiao) throws SQLException {
        RegiaoSustentavel regiao = regiaoSustentavelDAO.buscarPorId(idRegiao);
        if (regiao == null) {
            throw new SQLException("Região sustentável não encontrada para o ID: " + idRegiao);
        }
        return regiao;
    }

    // Método para listar todas as regiões sustentáveis
    public List<RegiaoSustentavel> listarTodasRegioes() throws SQLException {
        return regiaoSustentavelDAO.listarTodas();
    }

    // Método para atualizar uma região sustentável
    public void atualizarRegiao(RegiaoSustentavel regiao) throws Exception {
        validarCamposObrigatorios(regiao);

        // Atualiza no banco
        regiaoSustentavelDAO.atualizar(regiao);
    }

    // Método para deletar uma região sustentável pelo ID
    public void deletarRegiao(int idRegiao) throws SQLException {
        RegiaoSustentavel regiao = buscarPorId(idRegiao);
        if (regiao == null) {
            throw new SQLException("Região sustentável não encontrada para exclusão.");
        }

        regiaoSustentavelDAO.deletar(idRegiao);
    }

    // Valida os campos obrigatórios da região
    private void validarCamposObrigatorios(RegiaoSustentavel regiao) throws Exception {
        if (regiao.getNomeRegiao() == null || regiao.getNomeRegiao().trim().isEmpty()) {
            throw new Exception("O nome da região é obrigatório.");
        }
    }
}
