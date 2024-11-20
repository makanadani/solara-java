package br.com.solara.model.bo;

import br.com.solara.model.dao.TipoFonteDAO;
import br.com.solara.model.vo.TipoFonte;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TipoFonteBO {

    private final TipoFonteDAO tipoFonteDAO;

    // Construtor
    public TipoFonteBO(Connection connection) {
        this.tipoFonteDAO = new TipoFonteDAO(connection);
    }

    // Método para inserir um novo tipo de fonte com validações
    public void inserirTipoFonte(TipoFonte tipoFonte) throws Exception {
        validarCamposObrigatorios(tipoFonte);

        // Verifica se já existe um tipo de fonte com o mesmo nome
        if (isNomeFonteDuplicado(tipoFonte.getNomeFonte())) {
            throw new Exception("Já existe um tipo de fonte com este nome.");
        }

        // Insere o tipo de fonte no banco
        tipoFonteDAO.inserir(tipoFonte);
    }

    // Método para buscar um tipo de fonte pelo ID
    public TipoFonte buscarPorId(int idTipoFonte) throws SQLException {
        TipoFonte tipoFonte = tipoFonteDAO.buscarPorId(idTipoFonte);
        if (tipoFonte == null) {
            throw new SQLException("Tipo de fonte não encontrado para o ID: " + idTipoFonte);
        }
        return tipoFonte;
    }

    // Método para listar todos os tipos de fonte
    public List<TipoFonte> listarTodosTiposFonte() throws SQLException {
        return tipoFonteDAO.listarTodos();
    }

    // Método para atualizar um tipo de fonte
    public void atualizarTipoFonte(TipoFonte tipoFonte) throws Exception {
        validarCamposObrigatorios(tipoFonte);

        // Atualiza o tipo de fonte no banco
        tipoFonteDAO.atualizar(tipoFonte);
    }

    // Método para deletar um tipo de fonte pelo ID
    public void deletarTipoFonte(int idTipoFonte) throws SQLException {
        TipoFonte tipoFonte = buscarPorId(idTipoFonte);
        if (tipoFonte == null) {
            throw new SQLException("Tipo de fonte não encontrado para exclusão.");
        }

        tipoFonteDAO.deletar(idTipoFonte);
    }

    // Método para validar campos obrigatórios
    private void validarCamposObrigatorios(TipoFonte tipoFonte) throws Exception {
        if (tipoFonte.getNomeFonte() == null || tipoFonte.getNomeFonte().trim().isEmpty()) {
            throw new Exception("O nome do tipo de fonte é obrigatório.");
        }
    }

    // Verifica se já existe um tipo de fonte com o mesmo nome
    private boolean isNomeFonteDuplicado(String nomeFonte) throws SQLException {
        List<TipoFonte> tiposFonte = tipoFonteDAO.listarTodos();
        return tiposFonte.stream().anyMatch(tf -> tf.getNomeFonte().equalsIgnoreCase(nomeFonte));
    }
}
