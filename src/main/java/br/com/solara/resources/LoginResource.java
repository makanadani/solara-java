package br.com.solara.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import br.com.solara.model.dao.EmpresaDAO;
import br.com.solara.model.vo.Empresa;

@Path("/login")
public class LoginResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response realizarLogin(Map<String, String> credentials) throws SQLException, ClassNotFoundException {
        String cnpj = credentials.get("cnpjEmpresa");
        String senha = credentials.get("senhaEmpresa");

        // Validação inicial de CNPJ e Senha
        if (cnpj == null || senha == null || cnpj.isEmpty() || senha.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "CNPJ e senha são obrigatórios."))
                    .build();
        }

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.buscarPorCnpj(cnpj); // Buscar empresa pelo CNPJ

        if (empresa == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "CNPJ não encontrado."))
                    .build();
        }

        // Verificação de senha
        if (empresa.getRazaoSocialEmpresa().equalsIgnoreCase("EcoMinds Ltda.")) {
            // Exceção para senha fixa (apenas para testes)
            if (!senha.equals("ecominds123")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "Senha inválida."))
                        .build();
            }
        } else {
            // Verificação de senha criptografada para outras empresas
            String senhaHash = gerarHashSHA256(senha); // Gera o hash SHA-256 da senha
            if (!empresa.getSenhaEmpresa().equals(senhaHash)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "Senha inválida."))
                        .build();
            }
        }

        // Login bem-sucedido
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login realizado com sucesso!");
        response.put("idEmpresa", String.valueOf(empresa.getIdEmpresa()));
        response.put("razaoSocial", empresa.getRazaoSocialEmpresa());

        return Response.ok(response).build();
    }

    // Método para gerar hash SHA-256
    private String gerarHashSHA256(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash SHA-256: " + e.getMessage(), e);
        }
    }
}
