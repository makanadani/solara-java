package br.com.solara.model.bo;

import br.com.solara.model.dao.EmissaoCarbonoDAO;
import br.com.solara.model.vo.EmissaoCarbono;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmissaoCarbonoBO {

    private final EmissaoCarbonoDAO emissaoCarbonoDAO;

    // Construtor
    public EmissaoCarbonoBO(Connection connection) {
        this.emissaoCarbonoDAO = new EmissaoCarbonoDAO(connection);
    }

    // Método para inserir uma nova emissão de carbono com validações
    public void inserirEmissao(EmissaoCarbono emissao) throws Exception {
        validarCamposObrigatorios(emissao);
        validarEmissaoPositiva(emissao.getEmissao());

        // Insere a emissão no banco
        emissaoCarbonoDAO.inserir(emissao);
    }

    // Método para buscar uma emissão pelo ID
    public EmissaoCarbono buscarPorId(int idEmissao) throws SQLException {
        EmissaoCarbono emissao = emissaoCarbonoDAO.buscarPorId(idEmissao);
        if (emissao == null) {
            throw new SQLException("Emissão de carbono não encontrada para o ID: " + idEmissao);
        }
        return emissao;
    }

    // Método para listar todas as emissões de carbono
    public List<EmissaoCarbono> listarTodasEmissoes() throws SQLException {
        return emissaoCarbonoDAO.listarTodas();
    }

    // Método para atualizar uma emissão de carbono
    public void atualizarEmissao(EmissaoCarbono emissao) throws Exception {
        validarCamposObrigatorios(emissao);
        validarEmissaoPositiva(emissao.getEmissao());

        // Atualiza a emissão no banco
        emissaoCarbonoDAO.atualizar(emissao);
    }

    // Método para deletar uma emissão pelo ID
    public void deletarEmissao(int idEmissao) throws SQLException {
        EmissaoCarbono emissao = buscarPorId(idEmissao);
        if (emissao == null) {
            throw new SQLException("Emissão de carbono não encontrada para exclusão.");
        }

        emissaoCarbonoDAO.deletar(idEmissao);
    }

    // Método para validar campos obrigatórios
    private void validarCamposObrigatorios(EmissaoCarbono emissao) throws Exception {
        if (emissao.getIdTipoFonte() <= 0) {
            throw new Exception("O ID do tipo de fonte é obrigatório e deve ser maior que zero.");
        }
        if (emissao.getEmissao() < 0) {
            throw new Exception("O valor da emissão de carbono não pode ser negativo.");
        }
    }

    // Método para garantir que a emissão seja positiva
    private void validarEmissaoPositiva(double emissao) throws Exception {
        if (emissao <= 0) {
            throw new Exception("A emissão de carbono deve ser um valor positivo.");
        }
    }
}
