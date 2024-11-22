package br.com.solara.model.bo;

import br.com.solara.model.dao.ProjetoSustentavelDAO;
import br.com.solara.model.vo.ProjetoSustentavel;

import java.sql.SQLException;
import java.util.List;

// Classe de regras de negócio para projetos sustentáveis
public class ProjetoSustentavelBO {

    private final ProjetoSustentavelDAO projetoSustentavelDAO;

    // Construtor
    public ProjetoSustentavelBO() throws ClassNotFoundException, SQLException {
        this.projetoSustentavelDAO = new ProjetoSustentavelDAO();
    }

    // Inserir
    public String inserirBO(ProjetoSustentavel projeto) throws Exception {
    	
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(projeto);

        // 2. Insere o projeto no banco de dados.
        return projetoSustentavelDAO.inserir(projeto);
    }

    // Atualizar
    public String atualizarBO(ProjetoSustentavel projeto) throws Exception {
    	
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(projeto);

        // 2. Atualiza os dados do projeto no banco de dados.
        return projetoSustentavelDAO.atualizar(projeto);
    }

    // Deletar
    public String deletarBO(int idProjeto) throws SQLException {
    	
        // Regras de negócio:
        // 1. Verifica se o projeto existe antes de excluí-lo.
        ProjetoSustentavel projeto = buscarPorId(idProjeto);
        if (projeto == null) {
            throw new SQLException("Projeto sustentável não encontrado para exclusão.");
        }

        // 2. Realiza a exclusão do projeto no banco de dados.
        return projetoSustentavelDAO.deletar(idProjeto);
    }

    // Selecionar Todos
    public List<ProjetoSustentavel> listarTodosProjetos() throws SQLException {
        // Retorna todos os projetos sustentáveis cadastrados no banco.
        return projetoSustentavelDAO.selecionarTodos();
    }

    // Selecionar por ID
    public ProjetoSustentavel buscarPorId(int idProjeto) throws SQLException {
        ProjetoSustentavel projeto = projetoSustentavelDAO.selecionar(idProjeto);

        // Regras de negócio:
        // 1. Verifica se o projeto foi encontrado.
        if (projeto == null) {
            throw new SQLException("Projeto sustentável não encontrado para o ID: " + idProjeto);
        }

        return projeto;
    }

    // Validação de campos obrigatórios
    private void validarCamposObrigatorios(ProjetoSustentavel projeto) throws Exception {
    	
        // Regras de negócio:
        // 1. A descrição do projeto é obrigatória.
        if (projeto.getDescricaoProjeto() == null || projeto.getDescricaoProjeto().trim().isEmpty()) {
            throw new Exception("A descrição do projeto é obrigatória.");
        }

        // 2. O custo do projeto deve ser maior que zero.
        if (projeto.getCustoProjeto() == null || projeto.getCustoProjeto() <= 0) {
            throw new Exception("O custo do projeto deve ser maior que zero.");
        }

        // 3. O status do projeto é obrigatório.
        if (projeto.getStatusProjeto() == null || projeto.getStatusProjeto().trim().isEmpty()) {
            throw new Exception("O status do projeto é obrigatório.");
        }
    }
}
