package br.com.solara.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
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

        if (cnpj == null || senha == null || cnpj.isEmpty() || senha.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("CNPJ e senha são obrigatórios.").build();
        }

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.buscarPorCnpj(cnpj);

        if (empresa == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("CNPJ não encontrado.").build();
        }

        // Verificar senha
        if (empresa.getRazaoSocialEmpresa().equals("EcoMinds Ltda.")) {
            // Exceção: senha sem criptografia para "EcoMinds Ltda."
            if (!senha.equals("ecominds123")) { // Senha fixa de teste
                return Response.status(Response.Status.UNAUTHORIZED).entity("Senha inválida.").build();
            }
        } else {
            // Verificar senha criptografada para outras empresas
            String senhaHash = gerarHashSHA256(senha); // Método para gerar hash SHA-256
            if (!empresa.getSenhaEmpresa().equals(senhaHash)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Senha inválida.").build();
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
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes("UTF-8"));
            return java.util.Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash SHA-256: " + e.getMessage(), e);
        }
    }
}
