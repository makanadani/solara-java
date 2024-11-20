package br.com.solara.model.bo;

import br.com.solara.model.dao.ComunidadeDAO;
import br.com.solara.model.vo.Comunidade;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ComunidadeBO {

    private final ComunidadeDAO comunidadeDAO;

    // Construtor
    public ComunidadeBO(Connection connection) {
        this.comunidadeDAO = new ComunidadeDAO(connection);
    }

    // Método para inserir uma nova comunidade com validações
    public void inserirComunidade(Comunidade comunidade) throws Exception {
        validarCamposObrigatorios(comunidade);
        validarLocalizacao(comunidade.getLatitudeComunidade(), comunidade.getLongitudeComunidade());

        // Verifica se já existe uma comunidade com o mesmo nome
        if (isNomeComunidadeDuplicado(comunidade.getNomeComunidade())) {
            throw new Exception("Já existe uma comunidade com este nome.");
        }

        // Insere no banco
        comunidadeDAO.inserir(comunidade);
    }

    // Método para buscar uma comunidade pelo ID
    public Comunidade buscarComunidadePorId(int idComunidade) throws SQLException {
        Comunidade comunidade = comunidadeDAO.buscarPorId(idComunidade);
        if (comunidade == null) {
            throw new SQLException("Comunidade não encontrada para o ID: " + idComunidade);
        }
        return comunidade;
    }

    // Método para listar todas as comunidades
    public List<Comunidade> listarTodasComunidades() throws SQLException {
        return comunidadeDAO.listarTodas();
    }

    // Método para atualizar uma comunidade
    public void atualizarComunidade(Comunidade comunidade) throws Exception {
        validarCamposObrigatorios(comunidade);
        validarLocalizacao(comunidade.getLatitudeComunidade(), comunidade.getLongitudeComunidade());

        // Atualiza no banco
        comunidadeDAO.atualizar(comunidade);
    }

    // Método para deletar uma comunidade pelo ID
    public void deletarComunidade(int idComunidade) throws SQLException {
        Comunidade comunidade = buscarComunidadePorId(idComunidade);
        if (comunidade == null) {
            throw new SQLException("Comunidade não encontrada para exclusão.");
        }

        comunidadeDAO.deletar(idComunidade);
    }

    // Valida os campos obrigatórios
    private void validarCamposObrigatorios(Comunidade comunidade) throws Exception {
        if (comunidade.getNomeComunidade() == null || comunidade.getNomeComunidade().trim().isEmpty()) {
            throw new Exception("O nome da comunidade é obrigatório.");
        }
        if (comunidade.getIdEmpresa() <= 0) {
            throw new Exception("A comunidade deve estar associada a uma empresa válida.");
        }
        if (comunidade.getIdRegiao() <= 0) {
            throw new Exception("A comunidade deve estar associada a uma região válida.");
        }
    }

    // Valida se a localização está dentro de limites razoáveis
    private void validarLocalizacao(double latitude, double longitude) throws Exception {
        if (latitude < -90 || latitude > 90) {
            throw new Exception("A latitude deve estar entre -90 e 90 graus.");
        }
        if (longitude < -180 || longitude > 180) {
            throw new Exception("A longitude deve estar entre -180 e 180 graus.");
        }
    }

    // Verifica se já existe uma comunidade com o mesmo nome
    private boolean isNomeComunidadeDuplicado(String nomeComunidade) throws SQLException {
        List<Comunidade> comunidades = comunidadeDAO.listarTodas();
        return comunidades.stream().anyMatch(c -> c.getNomeComunidade().equalsIgnoreCase(nomeComunidade));
    }
}
