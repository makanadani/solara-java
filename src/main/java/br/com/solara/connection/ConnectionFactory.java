import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection conexao() throws ClassNotFoundException, SQLException {

        // Carrega o driver do Oracle
        Class.forName("oracle.jdbc.OracleDriver");

        // Pega variáveis de ambiente ou utiliza valores padrão para testes locais
        String dbUrl = System.getenv("ORACLE_DB_URL");
        String username = System.getenv("ORACLE_DB_USER");
        String password = System.getenv("ORACLE_DB_PASSWORD");

        // Fallback para valores padrão (apenas para testes locais)
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl"; // URL padrão
        }
        if (username == null || username.isEmpty()) {
            username = "rm558404"; // Usuário padrão
        }
        if (password == null || password.isEmpty()) {
            password = "090790"; // Senha padrão
        }

        // Retorna a conexão
        return DriverManager.getConnection(dbUrl, username, password);
    }
}
