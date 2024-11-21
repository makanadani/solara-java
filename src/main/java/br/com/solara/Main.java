package br.com.solara;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
    public static void main(String[] args) throws Exception {
        // Criação do servidor na porta 8080
        Server server = new Server(8080);

        // Configuração do contexto da aplicação
        WebAppContext context = new WebAppContext();
        context.setContextPath("/"); // Define a raiz do contexto
        context.setWar("target/solara-java-0.0.1.war"); // Caminho para o WAR gerado

        // Define o contexto como o handler do servidor
        server.setHandler(context);

        // Inicializa o servidor
        try {
            server.start(); // Inicia o servidor
            System.out.println("Servidor iniciado em http://localhost:8080");
            server.join(); // Mantém o servidor em execução
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
