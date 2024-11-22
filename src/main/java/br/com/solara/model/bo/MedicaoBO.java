package br.com.solara.model.bo;

import br.com.solara.model.dao.MedicaoDAO;
import br.com.solara.model.vo.Medicao;

import java.sql.SQLException;
import java.util.List;

public class MedicaoBO {

    private final MedicaoDAO medicaoDAO;

    // Construtor
    public MedicaoBO() throws ClassNotFoundException, SQLException {
        this.medicaoDAO = new MedicaoDAO();
    }

    // Método para inserir uma nova medição
    public void inserirBO(Medicao medicao) throws Exception {
        // Valida campos obrigatórios antes de inserir
        validarCamposObrigatorios(medicao);

        medicaoDAO.inserir(medicao);
    }

    // Método para buscar uma medição pelo ID
    public Medicao selecionarBO(int idMedicao) throws SQLException {
        if (idMedicao <= 0) {
            throw new IllegalArgumentException("O ID da medição deve ser maior que zero.");
        }

        Medicao medicao = medicaoDAO.selecionar(idMedicao);
        if (medicao == null) {
            throw new SQLException("Medição não encontrada para o ID fornecido.");
        }

        return medicao;
    }

    // Método para listar todas as medições
    public List<Medicao> selecionarTodosBO() throws SQLException {
        return medicaoDAO.selecionarTodos();
    }

    // Método para atualizar uma medição existente
    public void atualizarBO(Medicao medicao) throws Exception {
        if (medicao.getIdMedicao() <= 0) {
            throw new IllegalArgumentException("O ID da medição para atualização deve ser válido.");
        }

        validarCamposObrigatorios(medicao);
        medicaoDAO.atualizar(medicao);
    }

    // Método para deletar uma medição pelo ID
    public void deletarBO(int idMedicao) throws SQLException {
        if (idMedicao <= 0) {
            throw new IllegalArgumentException("O ID da medição deve ser maior que zero.");
        }

        medicaoDAO.deletar(idMedicao);
    }

    // Validação de campos obrigatórios
    private void validarCamposObrigatorios(Medicao medicao) throws Exception {
        if (medicao.getIdSensor() <= 0) {
            throw new Exception("A medição deve estar associada a um sensor válido.");
        }
        if (medicao.getTipoMedicao() == null || medicao.getTipoMedicao().trim().isEmpty()) {
            throw new Exception("O tipo de medição é obrigatório.");
        }
        if (!medicao.getTipoMedicao().equalsIgnoreCase("Produção") &&
            !medicao.getTipoMedicao().equalsIgnoreCase("Armazenamento") &&
            !medicao.getTipoMedicao().equalsIgnoreCase("Consumo")) {
            throw new Exception("O tipo de medição deve ser 'Produção', 'Armazenamento' ou 'Consumo'.");
        }
        if (medicao.getValorMedicao() <= 0) {
            throw new Exception("O valor da medição deve ser maior que zero.");
        }
    }
}
