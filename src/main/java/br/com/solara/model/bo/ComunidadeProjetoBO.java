package br.com.solara.model.bo;

import br.com.solara.model.dao.ComunidadeProjetoDAO;
import br.com.solara.model.vo.ComunidadeProjeto;

import java.sql.SQLException;
import java.util.List;

// Classe de regras de negócio para associações entre comunidades e projetos
public class ComunidadeProjetoBO {

    private final ComunidadeProjetoDAO comunidadeProjetoDAO;

    public ComunidadeProjetoBO() throws ClassNotFoundException, SQLException {
        this.comunidadeProjetoDAO = new ComunidadeProjetoDAO();
    }

    // Associar uma comunidade a um projeto
    public String associarComunidadeProjeto(ComunidadeProjeto comunidadeProjeto, String descricaoProjeto) throws Exception {
    	
        // Regras de negócio:
        // 1. Valida os campos obrigatórios.
        validarCamposObrigatorios(comunidadeProjeto);

        // 2. Verifica se já existe uma associação duplicada com base na descrição do projeto.
        if (isRelacaoDuplicada(comunidadeProjeto.getIdComunidade(), descricaoProjeto)) {
            throw new Exception("A comunidade já está associada a um projeto com esta descrição.");
        }

        // 3. Insere a relação no banco de dados.
        comunidadeProjetoDAO.inserir(comunidadeProjeto);
        return "Comunidade associada ao projeto com sucesso!";
    }

    // Listar todas as associações entre comunidades e projetos
    public List<ComunidadeProjeto> selecionarTodosBO() throws SQLException {
        return comunidadeProjetoDAO.selecionarTodos();
    }

    // Buscar associações por ID da comunidade
    public List<ComunidadeProjeto> selecionarPorIdComunidadeBO(int idComunidade) throws SQLException {
        return comunidadeProjetoDAO.selecionarTodos();
    }

    // Buscar associações por ID do projeto
    public List<ComunidadeProjeto> selecionarPorIdProjetoBO(int idProjeto) throws SQLException {
        return comunidadeProjetoDAO.selecionarPorIdProjeto(idProjeto);
    }

    // Remover uma relação específica entre comunidade e projeto
    public String removerRelacao(int idComunidade, int idProjeto) throws SQLException {
    	
        // Regras de negócio:
        // 1. Verifica se a relação existe.
        if (!isRelacaoExistente(idComunidade, idProjeto)) {
            throw new SQLException("Relação entre comunidade e projeto não encontrada.");
        }

        // 2. Realiza a exclusão da relação no banco de dados.
        comunidadeProjetoDAO.deletar(idComunidade, idProjeto);
        return "Relação entre comunidade e projeto removida com sucesso!";
    }

    // Remover todas as relações de uma comunidade
    public String deletarPorIdComunidadeBO(int idComunidade) throws SQLException {
        comunidadeProjetoDAO.deletarPorIdComunidade(idComunidade);
        return "Todas as relações da comunidade foram removidas com sucesso!";
    }

    // Remover todas as relações de um projeto
    public String deletarPorIdProjetoBO(int idProjeto) throws SQLException {
        comunidadeProjetoDAO.deletarPorIdProjeto(idProjeto);
        return "Todas as relações do projeto foram removidas com sucesso!";
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

    // Verifica se uma relação entre comunidade e descrição do projeto já existe
    private boolean isRelacaoDuplicada(int idComunidade, String descricaoProjeto) throws SQLException {
        List<String> descricoesProjetos = comunidadeProjetoDAO.selecionarPorIdComunidade(idComunidade);
        return descricoesProjetos.stream().anyMatch(descricao -> descricao.equalsIgnoreCase(descricaoProjeto));
    }

    // Verifica se uma relação entre comunidade e id do projeto existe
    private boolean isRelacaoExistente(int idComunidade, int idProjeto) throws SQLException {
        List<ComunidadeProjeto> relacoes = comunidadeProjetoDAO.selecionarPorIdProjeto(idProjeto);
        return relacoes.stream().anyMatch(relacao -> relacao.getIdComunidade() == idComunidade);
    }
}
