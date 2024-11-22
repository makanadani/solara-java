package br.com.solara.model.bo;

import br.com.solara.model.dao.EmpresaDAO;
import br.com.solara.model.vo.Empresa;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmpresaBO {

    private final EmpresaDAO empresaDAO;

    public EmpresaBO() throws ClassNotFoundException, SQLException {
        this.empresaDAO = new EmpresaDAO();
    }

    // Inserir
    public void inserirBO(Empresa empresa) throws Exception {
        // Regras de negócio:
        // 1. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(empresa);

        // 2. Verifica se o CNPJ já está cadastrado.
        if (empresaDAO.selecionarPorCnpj(empresa.getCnpjEmpresa()) != null) {
            throw new Exception("Já existe uma empresa cadastrada com este CNPJ.");
        }

        // 3. Insere a empresa no banco de dados.
        empresaDAO.inserir(empresa);
    }

    // Atualizar
    public void atualizarBO(Empresa empresa) throws Exception {
        // Regras de negócio:
        // 1. O ID da empresa deve ser válido.
        if (empresa.getIdEmpresa() <= 0) {
            throw new Exception("O ID da empresa para atualização deve ser válido.");
        }

        // 2. Valida se os campos obrigatórios estão preenchidos.
        validarCamposObrigatorios(empresa);

        // 3. Atualiza a empresa no banco de dados.
        empresaDAO.atualizar(empresa);
    }

    // Deletar
    public void deletarBO(int idEmpresa) throws SQLException {
        // Regras de negócio:
        // 1. Verifica se a empresa existe antes de excluí-la.
        Empresa empresa = selecionarPorIdBO(idEmpresa);
        if (empresa == null) {
            throw new SQLException("Empresa não encontrada para exclusão.");
        }

        // 2. Realiza a exclusão da empresa no banco de dados.
        empresaDAO.deletar(idEmpresa);
    }

    // Selecionar Todos
    public ArrayList<Empresa> selecionarTodosBO() throws SQLException {
        return (ArrayList<Empresa>) empresaDAO.selecionarTodos();
    }

    // Selecionar por ID
    public Empresa selecionarPorIdBO(int idEmpresa) throws SQLException {
        Empresa empresa = empresaDAO.selecionarPorId(idEmpresa);

        // Regras de negócio:
        // 1. Verifica se a empresa foi encontrada.
        if (empresa == null) {
            throw new SQLException("Empresa não encontrada para o ID fornecido.");
        }

        return empresa;
    }

    // Selecionar por CNPJ
    public Empresa selecionarPorCnpjBO(String cnpj) throws Exception {
        // Regras de negócio:
        // 1. O CNPJ não pode estar vazio.
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new Exception("O CNPJ da empresa é obrigatório.");
        }

        // 2. Busca a empresa pelo CNPJ no banco de dados.
        Empresa empresa = empresaDAO.selecionarPorCnpj(cnpj);

        // 3. Verifica se a empresa foi encontrada.
        if (empresa == null) {
            throw new Exception("Empresa não encontrada para o CNPJ fornecido.");
        }

        return empresa;
    }

    // Validação de campos obrigatórios
    private void validarCamposObrigatorios(Empresa empresa) throws Exception {
        if (empresa.getRazaoSocialEmpresa() == null || empresa.getRazaoSocialEmpresa().trim().isEmpty()) {
            throw new Exception("A razão social da empresa é obrigatória.");
        }
        if (empresa.getCnpjEmpresa() == null || empresa.getCnpjEmpresa().trim().isEmpty()) {
            throw new Exception("O CNPJ da empresa é obrigatório.");
        }
        if (empresa.getSenhaEmpresa() == null || empresa.getSenhaEmpresa().trim().isEmpty()) {
            throw new Exception("A senha da empresa é obrigatória.");
        }
    }
}
