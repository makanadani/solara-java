package br.com.solara.model.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.solara.model.dao.TipoFonteDAO;
import br.com.solara.model.vo.TipoFonte;

public class TipoFonteBO {

    TipoFonteDAO tipoFonteDAO = null;

    // Inserir
    public void inserirBO(TipoFonte tipoFonte, String tipoSensor) throws ClassNotFoundException, SQLException, Exception {
        TipoFonteDAO tipoFonteDAO = new TipoFonteDAO();

        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(tipoFonte);

        // 2. Verifica se já existe um tipo de fonte com o mesmo nome para evitar duplicidade.
        if (isNomeFonteDuplicado(tipoFonte.getNomeFonte())) {
            throw new Exception("Já existe um tipo de fonte com este nome.");
        }

        // 3. Garante que o tipo de fonte só pode ser associado a sensores de produção.
        if (!"Produção".equalsIgnoreCase(tipoSensor)) {
            throw new Exception("id_tipo_fonte só pode ser associado a sensores de produção.");
        }

        // Realiza a inserção do tipo de fonte.
        tipoFonteDAO.inserir(tipoFonte);
    }

    // Atualizar
    public void atualizarBO(TipoFonte tipoFonte, String tipoSensor) throws ClassNotFoundException, SQLException, Exception {
        TipoFonteDAO tipoFonteDAO = new TipoFonteDAO();

        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(tipoFonte);

        // 2. Garante que o tipo de fonte só pode ser associado a sensores de produção.
        if (!"Produção".equalsIgnoreCase(tipoSensor)) {
            throw new Exception("id_tipo_fonte só pode ser associado a sensores de produção.");
        }

        // Realiza a atualização do tipo de fonte.
        tipoFonteDAO.atualizar(tipoFonte);
    }

    // Deletar
    public void deletarBO(int idTipoFonte) throws ClassNotFoundException, SQLException {
        TipoFonteDAO tipoFonteDAO = new TipoFonteDAO();

        // Regras de negócio:
        // 1. Verifica se o tipo de fonte existe antes de tentar excluí-lo.
        TipoFonte tipoFonte = tipoFonteDAO.selecionar(idTipoFonte);
        if (tipoFonte == null) {
            throw new SQLException("Tipo de fonte não encontrado para exclusão.");
        }

        // Realiza a exclusão do tipo de fonte.
        tipoFonteDAO.deletar(idTipoFonte);
    }

    // Selecionar todos
    public ArrayList<TipoFonte> selecionarBO() throws ClassNotFoundException, SQLException {
        TipoFonteDAO tipoFonteDAO = new TipoFonteDAO();

        // Retorna a lista de todos os tipos de fonte.
        return (ArrayList<TipoFonte>) tipoFonteDAO.selecionarTodos();
    }

    // Valida se os campos obrigatórios foram preenchidos.
    private void validarCamposObrigatorios(TipoFonte tipoFonte) throws Exception {
        // Verifica se o nome do tipo de fonte foi informado.
        if (tipoFonte.getNomeFonte() == null || tipoFonte.getNomeFonte().trim().isEmpty()) {
            throw new Exception("O nome do tipo de fonte é obrigatório.");
        }
    }

    // Verifica se já existe um tipo de fonte com o mesmo nome no banco de dados.
    private boolean isNomeFonteDuplicado(String nomeFonte) throws ClassNotFoundException, SQLException {
        TipoFonteDAO tipoFonteDAO = new TipoFonteDAO();

        // Retorna true se houver duplicidade.
        List<TipoFonte> tiposFonte = tipoFonteDAO.selecionarTodos();
        return tiposFonte.stream().anyMatch(tf -> tf.getNomeFonte().equalsIgnoreCase(nomeFonte));
    }
}
