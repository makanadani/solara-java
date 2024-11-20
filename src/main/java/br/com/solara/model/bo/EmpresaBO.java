package br.com.solara.model.bo;

import java.sql.SQLException;
import java.util.List;

import br.com.solara.model.dao.EmpresaDAO;
import br.com.solara.model.vo.Empresa;

public class EmpresaBO {

    // Inserir
    public void inserirBO(Empresa empresa) throws ClassNotFoundException, SQLException {
        EmpresaDAO empresaDAO = new EmpresaDAO();

        // Validações de negócio
        validarCamposObrigatorios(empresa);
        validarCNPJ(empresa.getCnpjEmpresa());

        // Verifica se o CNPJ já está cadastrado
        if (isCnpjCadastrado(empresa.getCnpjEmpresa())) {
            throw new SQLException("O CNPJ informado já está cadastrado.");
        }

        empresaDAO.inserir(empresa);
    }

    // Atualizar
    public void atualizarBO(Empresa empresa) throws ClassNotFoundException, SQLException {
        EmpresaDAO empresaDAO = new EmpresaDAO();

        // Validações de negócio
        validarCamposObrigatorios(empresa);
        validarCNPJ(empresa.getCnpjEmpresa());

        empresaDAO.atualizar(empresa);
    }

    // Deletar
    public void deletarBO(int idEmpresa) throws ClassNotFoundException, SQLException {
        EmpresaDAO empresaDAO = new EmpresaDAO();

        // Validação de existência
        Empresa empresa = empresaDAO.buscarPorId(idEmpresa);
        if (empresa == null) {
            throw new SQLException("Empresa não encontrada para exclusão.");
        }

        empresaDAO.deletar(idEmpresa);
    }

    // Selecionar todas
    public List<Empresa> selecionarBO() throws ClassNotFoundException, SQLException {
        EmpresaDAO empresaDAO = new EmpresaDAO();
        return empresaDAO.selecionar();
    }

    // Buscar por ID
    public Empresa buscarEmpresaPorId(int idEmpresa) throws ClassNotFoundException, SQLException {
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.buscarPorId(idEmpresa);

        if (empresa == null) {
            throw new SQLException("Empresa não encontrada para o ID informado.");
        }

        return empresa;
    }

    // Validação de Campos Obrigatórios
    private void validarCamposObrigatorios(Empresa empresa) throws SQLException {
        if (empresa.getNomeEmpresa() == null || empresa.getNomeEmpresa().trim().isEmpty()) {
            throw new SQLException("O nome da empresa é obrigatório.");
        }
        if (empresa.getCnpjEmpresa() == null || empresa.getCnpjEmpresa().trim().isEmpty()) {
            throw new SQLException("O CNPJ da empresa é obrigatório.");
        }
    }

    // Validação de CNPJ
    private void validarCNPJ(String cnpj) throws SQLException {
        if (!isCnpjValido(cnpj)) {
            throw new SQLException("O CNPJ informado é inválido.");
        }
    }

    private boolean isCnpjValido(String cnpj) {
        // Validação simplificada: garantir que tenha 14 caracteres numéricos
        return cnpj != null && cnpj.matches("\\d{14}");
    }

    // Verificar se CNPJ está cadastrado
    private boolean isCnpjCadastrado(String cnpj) throws ClassNotFoundException, SQLException {
        EmpresaDAO empresaDAO = new EmpresaDAO();
        List<Empresa> empresas = empresaDAO.selecionar();
        return empresas.stream().anyMatch(e -> e.getCnpjEmpresa().equals(cnpj));
    }
}
