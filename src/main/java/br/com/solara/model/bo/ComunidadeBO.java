package br.com.solara.model.bo;

import br.com.solara.model.dao.ComunidadeDAO;
import br.com.solara.model.vo.Comunidade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComunidadeBO {

    private final ComunidadeDAO comunidadeDAO;

    // Construtor
    public ComunidadeBO() throws ClassNotFoundException, SQLException {
        this.comunidadeDAO = new ComunidadeDAO();
    }

    // Inserir
    public void inserirBO(Comunidade comunidade) throws Exception {
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(comunidade);

        // 2. Valida a localização da comunidade.
        validarLocalizacao(comunidade.getLatitudeComunidade(), comunidade.getLongitudeComunidade());

        // 3. Verifica se já existe uma comunidade com o mesmo nome.
        if (isNomeComunidadeDuplicado(comunidade.getNomeComunidade())) {
            throw new Exception("Já existe uma comunidade com este nome.");
        }

        // 4. Insere a comunidade no banco de dados.
        comunidadeDAO.inserir(comunidade);
    }

    // Atualizar
    public void atualizarBO(Comunidade comunidade) throws Exception {
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(comunidade);

        // 2. Valida a localização da comunidade.
        validarLocalizacao(comunidade.getLatitudeComunidade(), comunidade.getLongitudeComunidade());

        // 3. Atualiza a comunidade no banco de dados.
        comunidadeDAO.atualizar(comunidade);
    }

    // Deletar
    public void deletarBO(int idComunidade) throws SQLException {
        // Regras de negócio:
        // 1. Verifica se a comunidade existe antes de excluí-la.
        Comunidade comunidade = selecionarBO(idComunidade);
        if (comunidade == null) {
            throw new SQLException("Comunidade não encontrada para exclusão.");
        }

        // 2. Realiza a exclusão da comunidade no banco de dados.
        comunidadeDAO.deletar(idComunidade);
    }

    // Selecionar Todos
    public ArrayList<Comunidade> selecionarTodosBO() throws SQLException {
        return (ArrayList<Comunidade>) comunidadeDAO.selecionarTodos();
    }

    // Selecionar por ID
    public Comunidade selecionarBO(int idComunidade) throws SQLException {
        Comunidade comunidade = comunidadeDAO.selecionar(idComunidade);

        // Regras de negócio:
        // 1. Verifica se a comunidade foi encontrada.
        if (comunidade == null) {
            throw new SQLException("Comunidade não encontrada para o ID fornecido.");
        }

        return comunidade;
    }

    // Validação de campos obrigatórios
    private void validarCamposObrigatorios(Comunidade comunidade) throws Exception {
        // 1. O nome da comunidade é obrigatório.
        if (comunidade.getNomeComunidade() == null || comunidade.getNomeComunidade().trim().isEmpty()) {
            throw new Exception("O nome da comunidade é obrigatório.");
        }

        // 2. A comunidade deve estar associada a uma empresa válida.
        if (comunidade.getIdEmpresa() <= 0) {
            throw new Exception("A comunidade deve estar associada a uma empresa válida.");
        }

        // 3. A comunidade deve estar associada a uma região válida.
        if (comunidade.getIdRegiao() <= 0) {
            throw new Exception("A comunidade deve estar associada a uma região válida.");
        }
    }

    // Validação da localização
    private void validarLocalizacao(double latitude, double longitude) throws Exception {
        // 1. A latitude deve estar entre -90 e 90 graus.
        if (latitude < -90 || latitude > 90) {
            throw new Exception("A latitude deve estar entre -90 e 90 graus.");
        }

        // 2. A longitude deve estar entre -180 e 180 graus.
        if (longitude < -180 || longitude > 180) {
            throw new Exception("A longitude deve estar entre -180 e 180 graus.");
        }
    }

    // Verifica se já existe uma comunidade com o mesmo nome
    private boolean isNomeComunidadeDuplicado(String nomeComunidade) throws SQLException {
        // Busca todas as comunidades e verifica duplicidade no nome.
        List<Comunidade> comunidades = comunidadeDAO.selecionarTodos();
        return comunidades.stream().anyMatch(c -> c.getNomeComunidade().equalsIgnoreCase(nomeComunidade));
    }
}
