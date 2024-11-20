package br.com.solara.services;

public class EmpresaService {
    private final ApiClient apiClient;

    public EmpresaService() {
        this.apiClient = new ApiClient();
    }

    public String obterDadosExternos(String endpoint) {
        try {
            return apiClient.executarChamada(endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao obter dados da API: " + e.getMessage();
        }
    }
}
