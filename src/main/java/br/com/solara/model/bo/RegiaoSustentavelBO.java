package br.com.solara.model.bo;

import br.com.solara.model.dao.RegiaoSustentavelDAO;
import br.com.solara.model.vo.RegiaoSustentavel;

import java.sql.SQLException;
import java.util.List;

// Classe de regras de negócio para regiões sustentáveis
public class RegiaoSustentavelBO {

    private final RegiaoSustentavelDAO regiaoSustentavelDAO;

    // Construtor
    public RegiaoSustentavelBO() throws ClassNotFoundException, SQLException {
        this.regiaoSustentavelDAO = new RegiaoSustentavelDAO();
    }

    // Inserir
    public String inserirBO(RegiaoSustentavel regiao) throws Exception {
    	
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(regiao);

        // 2. Insere a região no banco de dados.
        return regiaoSustentavelDAO.inserir(regiao);
    }

    // Atualizar
    public String atualizarBO(RegiaoSustentavel regiao) throws Exception {
    	
        // Regras de negócio:
        // 1. Verifica se o ID da região é válido.
        if (regiao.getIdRegiao() <= 0) {
            throw new Exception("O ID da região para atualização deve ser maior que zero.");
        }

        // 2. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(regiao);

        // 3. Atualiza os dados da região no banco de dados.
        return regiaoSustentavelDAO.atualizar(regiao);
    }

    // Deletar
    public String deletarBO(int idRegiao) throws SQLException {
    	
        // Regras de negócio:
        // 1. Verifica se o ID é válido.
        if (idRegiao <= 0) {
            throw new SQLException("O ID da região para exclusão deve ser maior que zero.");
        }

        // 2. Verifica se a região existe antes de excluí-la.
        RegiaoSustentavel regiao = selecionar(idRegiao);
        if (regiao == null) {
            throw new SQLException("Região sustentável não encontrada para exclusão.");
        }

        // 3. Realiza a exclusão da região no banco de dados.
        return regiaoSustentavelDAO.deletar(idRegiao);
    }

    // Selecionar Todos
    public List<RegiaoSustentavel> listarTodasRegioes() throws SQLException {
        // Retorna a lista de todas as regiões sustentáveis cadastradas.
        return regiaoSustentavelDAO.selecionarTodos();
    }

    // Selecionar por ID
    public RegiaoSustentavel selecionar(int idRegiao) throws SQLException {
    	
        // Regras de negócio:
        // 1. Verifica se o ID é válido.
        if (idRegiao <= 0) {
            throw new SQLException("O ID da região deve ser maior que zero.");
        }

        // 2. Busca a região pelo ID no banco de dados.
        RegiaoSustentavel regiao = regiaoSustentavelDAO.selecionar(idRegiao);

        // 3. Verifica se a região foi encontrada.
        if (regiao == null) {
            throw new SQLException("Região sustentável não encontrada para o ID: " + idRegiao);
        }

        return regiao;
    }

    // Validação de campos obrigatórios
    private void validarCamposObrigatorios(RegiaoSustentavel regiao) throws Exception {
    	
        // Regras de negócio:
        // 1. O nome da região é obrigatório e não pode estar vazio.
        if (regiao.getNomeRegiao() == null || regiao.getNomeRegiao().trim().isEmpty()) {
            throw new Exception("O nome da região é obrigatório.");
        }
    }
}
