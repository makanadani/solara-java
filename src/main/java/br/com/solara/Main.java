package br.com.solara;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
    public static void main(String[] args) throws Exception {
        // Configuração do servidor Jetty
        Server server = new Server(8080);

        // Define o contexto do aplicativo web
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setWar("target/solara-java-0.0.1.war");

        server.setHandler(context);

        // Inicia o servidor
        server.start();
        server.join();
    }
}
