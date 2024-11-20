package br.com.solara.model.bo;

import br.com.solara.model.dao.ComunidadeProjetoDAO;
import br.com.solara.model.vo.ComunidadeProjeto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ComunidadeProjetoBO {

    private final ComunidadeProjetoDAO comunidadeProjetoDAO;

    // Construtor
    public ComunidadeProjetoBO(Connection connection) {
        this.comunidadeProjetoDAO = new ComunidadeProjetoDAO(connection);
    }

    // Método para associar uma comunidade a um projeto
    public void associarComunidadeProjeto(ComunidadeProjeto comunidadeProjeto) throws Exception {
        validarCamposObrigatorios(comunidadeProjeto);

        // Verifica se a relação já existe
        if (isRelacaoDuplicada(comunidadeProjeto.getIdComunidade(), comunidadeProjeto.getIdProjeto())) {
            throw new Exception("A comunidade já está associada a este projeto.");
        }

        // Insere no banco
        comunidadeProjetoDAO.inserir(comunidadeProjeto);
    }

    // Método para listar todas as relações entre comunidades e projetos
    public List<ComunidadeProjeto> listarTodasRelacoes() throws SQLException {
        return comunidadeProjetoDAO.listarTodas();
    }

    // Método para buscar relações por ID da comunidade
    public List<ComunidadeProjeto> buscarPorIdComunidade(int idComunidade) throws SQLException {
        return comunidadeProjetoDAO.buscarPorIdComunidade(idComunidade);
    }

    // Método para buscar relações por ID do projeto
    public List<ComunidadeProjeto> buscarPorIdProjeto(int idProjeto) throws SQLException {
        return comunidadeProjetoDAO.buscarPorIdProjeto(idProjeto);
    }

    // Método para remover uma relação específica entre comunidade e projeto
    public void removerRelacao(int idComunidade, int idProjeto) throws SQLException {
        // Verifica se a relação existe antes de tentar deletar
        if (!isRelacaoExistente(idComunidade, idProjeto)) {
            throw new SQLException("Relação entre comunidade e projeto não encontrada.");
        }

        comunidadeProjetoDAO.deletar(idComunidade, idProjeto);
    }

    // Método para remover todas as relações de uma comunidade
    public void removerRelacoesPorIdComunidade(int idComunidade) throws SQLException {
        comunidadeProjetoDAO.deletarPorIdComunidade(idComunidade);
    }

    // Método para remover todas as relações de um projeto
    public void removerRelacoesPorIdProjeto(int idProjeto) throws SQLException {
        comunidadeProjetoDAO.deletarPorIdProjeto(idProjeto);
    }

    // Valida os campos obrigatórios
    private void validarCamposObrigatorios(ComunidadeProjeto comunidadeProjeto) throws Exception {
        if (comunidadeProjeto.getIdComunidade() <= 0) {
            throw new Exception("O ID da comunidade é obrigatório e deve ser maior que zero.");
        }
        if (comunidadeProjeto.getIdProjeto() <= 0) {
            throw new Exception("O ID do projeto é obrigatório e deve ser maior que zero.");
        }
    }

    // Verifica se uma relação entre comunidade e projeto já existe
    private boolean isRelacaoDuplicada(int idComunidade, int idProjeto) throws SQLException {
        List<ComunidadeProjeto> relacoes = comunidadeProjetoDAO.buscarPorIdComunidade(idComunidade);
        return relacoes.stream().anyMatch(relacao -> relacao.getIdProjeto() == idProjeto);
    }

    // Verifica se uma relação entre comunidade e projeto existe
    private boolean isRelacaoExistente(int idComunidade, int idProjeto) throws SQLException {
        List<ComunidadeProjeto> relacoes = comunidadeProjetoDAO.buscarPorIdComunidade(idComunidade);
        return relacoes.stream().anyMatch(relacao -> relacao.getIdProjeto() == idProjeto);
    }
}
