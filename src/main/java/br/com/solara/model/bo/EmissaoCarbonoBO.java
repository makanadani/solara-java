package br.com.solara.model.bo;

import br.com.solara.model.dao.EmissaoCarbonoDAO;
import br.com.solara.model.vo.EmissaoCarbono;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

// Classe de regras de negócio para emissões de carbono
public class EmissaoCarbonoBO {

    private final EmissaoCarbonoDAO emissaoCarbonoDAO;

    public EmissaoCarbonoBO(Connection connection) {
        this.emissaoCarbonoDAO = new EmissaoCarbonoDAO(connection);
    }

    // Inserir
    public String inserirBO(EmissaoCarbono emissao) throws Exception {
    	
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(emissao);

        // 2. Garante que o valor da emissão é positivo.
        validarEmissaoPositiva(emissao.getEmissao());

        // 3. Insere a emissão no banco.
        return emissaoCarbonoDAO.inserir(emissao);
    }

    // Atualizar
    public String atualizarBO(EmissaoCarbono emissao) throws Exception {
    	
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(emissao);

        // 2. Garante que o valor da emissão é positivo.
        validarEmissaoPositiva(emissao.getEmissao());

        // 3. Atualiza a emissão no banco.
        return emissaoCarbonoDAO.atualizar(emissao);
    }

    // Deletar
    public String deletarBO(int idEmissao) throws SQLException {
    	
        // Regras de negócio:
        // 1. Verifica se a emissão existe antes de excluí-la.
        EmissaoCarbono emissao = selecionarBO(idEmissao);
        if (emissao == null) {
            throw new SQLException("Emissão de carbono não encontrada para exclusão.");
        }

        // 2. Realiza a exclusão da emissão no banco.
        return emissaoCarbonoDAO.deletar(idEmissao);
    }

    // Selecionar por ID
    public EmissaoCarbono selecionarBO(int idEmissao) throws SQLException {
    	
        // Regras de negócio:
        // 1. Verifica se o ID é válido.
        if (idEmissao <= 0) {
            throw new SQLException("O ID da emissão deve ser maior que zero.");
        }

        // 2. Busca a emissão pelo ID no banco.
        EmissaoCarbono emissao = emissaoCarbonoDAO.selecionar(idEmissao);

        // 3. Verifica se a emissão foi encontrada.
        if (emissao == null) {
            throw new SQLException("Emissão de carbono não encontrada para o ID: " + idEmissao);
        }

        return emissao;
    }

    // Selecionar Todas
    public List<EmissaoCarbono> selecionarTodosBO() throws SQLException {
        // Retorna a lista de todas as emissões cadastradas no banco.
        return emissaoCarbonoDAO.selecionarTodos();
    }

    // Validação de campos obrigatórios
    private void validarCamposObrigatorios(EmissaoCarbono emissao) throws Exception {
        // Regras de negócio:
        // 1. O ID do tipo de fonte deve ser maior que zero.
        if (emissao.getIdTipoFonte() <= 0) {
            throw new Exception("O ID do tipo de fonte é obrigatório e deve ser maior que zero.");
        }

        // 2. O valor da emissão não pode ser negativo.
        if (emissao.getEmissao() < 0) {
            throw new Exception("O valor da emissão de carbono não pode ser negativo.");
        }
    }

    // Garante que a emissão seja positiva
    private void validarEmissaoPositiva(double emissao) throws Exception {
        // Regras de negócio:
        // 1. O valor da emissão deve ser positivo.
        if (emissao <= 0) {
            throw new Exception("A emissão de carbono deve ser um valor positivo.");
        }
    }
}
