package br.com.solara.services;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class ApiClient {
    private static final String API_KEY = System.getenv("HRKU-4c2bff34-2a8d-40f7-8f46-f37ca2b42dab"); // Lê a API Key das variáveis de ambiente

    public String executarChamada(String endpoint) throws Exception {
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new IllegalStateException("API Key não configurada. Verifique as variáveis de ambiente.");
        }

        // Constrói a URI e converte para URL
        URI uri = new URI(endpoint); // Valida a URI
        URL url = uri.toURL();       // Converte URI para URL

        // Abre a conexão HTTP
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY); // Passa a API Key no cabeçalho
        conn.setRequestProperty("Content-Type", "application/json");

        // Checa a resposta da API
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Erro ao chamar API: Código " + responseCode);
        }

        // Lê a resposta da API
        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }
}
