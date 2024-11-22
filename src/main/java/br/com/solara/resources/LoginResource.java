package br.com.solara.resources;

import br.com.solara.model.dao.EmpresaDAO;
import br.com.solara.model.vo.Empresa;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Path("/login")
public class LoginResource {

    private static final String SECRET_KEY = "SuaChaveSecreta";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response realizarLogin(Map<String, String> credentials) throws SQLException, ClassNotFoundException {
        String cnpj = credentials.get("cnpjEmpresa");
        String senha = credentials.get("senhaEmpresa");

        if (cnpj == null || senha == null || cnpj.isEmpty() || senha.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "CNPJ e senha são obrigatórios."))
                    .build();
        }

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.selecionarPorCnpj(cnpj);

        if (empresa == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "CNPJ não encontrado."))
                    .build();
        }

        String senhaHash = gerarHashSHA256(senha);
        if (!empresa.getSenhaEmpresa().equals(senhaHash)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Senha inválida."))
                    .build();
        }

        // Token para senha
        String token = gerarToken(empresa);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login realizado com sucesso!");
        response.put("token", token);
        response.put("idEmpresa", String.valueOf(empresa.getIdEmpresa()));
        response.put("razaoSocial", empresa.getRazaoSocialEmpresa());

        return Response.ok(response).build();
    }

    private String gerarToken(Empresa empresa) {
        String data = empresa.getIdEmpresa() + ":" + SECRET_KEY;
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

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
