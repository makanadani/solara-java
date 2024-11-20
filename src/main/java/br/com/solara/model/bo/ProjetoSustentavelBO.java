package br.com.solara.model.bo;

import br.com.solara.model.dao.ProjetoSustentavelDAO;
import br.com.solara.model.vo.ProjetoSustentavel;
import br.com.solara.exceptions.Exceptions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProjetoSustentavelBO {

    private final ProjetoSustentavelDAO projetoSustentavelDAO;

    public ProjetoSustentavelBO(Connection connection) {
        this.projetoSustentavelDAO = new ProjetoSustentavelDAO(connection);
    }

    public void inserirProjeto(ProjetoSustentavel projeto) throws ValidationException {
        validarCamposObrigatorios(projeto);
        try {
            projetoSustentavelDAO.inserir(projeto);
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir projeto sustentável: " + e.getMessage(), e);
        }
    }

    public ProjetoSustentavel buscarPorId(int idProjeto) throws NotFoundException {
        try {
            ProjetoSustentavel projeto = projetoSustentavelDAO.buscarPorId(idProjeto);
            if (projeto == null) {
                throw new NotFoundException("Projeto sustentável não encontrado para o ID: " + idProjeto);
            }
            return projeto;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar projeto sustentável: " + e.getMessage(), e);
        }
    }

    public List<ProjetoSustentavel> listarTodosProjetos() {
        try {
            return projetoSustentavelDAO.listarTodos();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar projetos sustentáveis: " + e.getMessage(), e);
        }
    }

    public void atualizarProjeto(ProjetoSustentavel projeto) throws ValidationException {
        validarCamposObrigatorios(projeto);
        try {
            projetoSustentavelDAO.atualizar(projeto);
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar projeto sustentável: " + e.getMessage(), e);
        }
    }

    public void deletarProjeto(int idProjeto) throws NotFoundException {
        try {
            ProjetoSustentavel projeto = buscarPorId(idProjeto);
            if (projeto == null) {
                throw new NotFoundException("Projeto sustentável não encontrado para exclusão.");
            }
            projetoSustentavelDAO.deletar(idProjeto);
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar projeto sustentável: " + e.getMessage(), e);
        }
    }

    private void validarCamposObrigatorios(ProjetoSustentavel projeto) throws ValidationException {
        if (projeto.getDescricaoProjeto() == null || projeto.getDescricaoProjeto().trim().isEmpty()) {
            throw new ValidationException("A descrição do projeto é obrigatória.");
        }
        if (projeto.getCustoProjeto() == null || projeto.getCustoProjeto() <= 0) {
            throw new ValidationException("O custo do projeto deve ser maior que zero.");
        }
        if (projeto.getStatusProjeto() == null || projeto.getStatusProjeto().trim().isEmpty()) {
            throw new ValidationException("O status do projeto é obrigatório.");
        }
    }
}
