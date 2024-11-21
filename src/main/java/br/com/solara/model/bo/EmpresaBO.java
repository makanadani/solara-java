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

        // Insere a empresa
        empresaDAO.inserir(empresa);
    }

    // Atualizar
    public void atualizarBO(Empresa empresa) throws ClassNotFoundException, SQLException {
        EmpresaDAO empresaDAO = new EmpresaDAO();

        // Validações de negócio
        validarCamposObrigatorios(empresa);
        validarCNPJ(empresa.getCnpjEmpresa());

        // Atualiza a empresa
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

        // Exclui a empresa
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
        if (empresa.getRazaoSocialEmpresa() == null || empresa.getRazaoSocialEmpresa().trim().isEmpty()) {
            throw new SQLException("A razão social da empresa é obrigatória.");
        }
        if (empresa.getCnpjEmpresa() == null || empresa.getCnpjEmpresa().trim().isEmpty()) {
            throw new SQLException("O CNPJ da empresa é obrigatório.");
        }
        if (empresa.getImagemEmpresa() == null || empresa.getImagemEmpresa().trim().isEmpty()) {
            throw new SQLException("A imagem da empresa é obrigatória.");
        }
        if (empresa.getDescricaoEmpresa() == null || empresa.getDescricaoEmpresa().trim().isEmpty()) {
            throw new SQLException("A descrição da empresa é obrigatória.");
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
