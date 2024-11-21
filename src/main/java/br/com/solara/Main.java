package br.com.solara;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setWar("target/solara-java-0.0.1.war");
        server.setHandler(context);
        server.start();
        server.join();
    }
}
