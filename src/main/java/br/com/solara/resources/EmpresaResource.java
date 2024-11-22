package br.com.solara.resources;

import br.com.solara.model.bo.EmpresaBO;
import br.com.solara.model.vo.Empresa;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/empresas")
public class EmpresaResource {

    private final EmpresaBO empresaBO;

    public EmpresaResource() throws SQLException, ClassNotFoundException {
        this.empresaBO = new EmpresaBO();
    }

    // Inserir
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarRs(Empresa empresa) {
        try {
            empresaBO.inserirBO(empresa);
            return Response.status(Response.Status.CREATED).entity("Empresa criada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar empresa: " + e.getMessage()).build();
        }
    }

    // Atualizar
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarRs(@PathParam("id") int id, Empresa empresa) {
        try {
            empresa.setIdEmpresa(id);
            empresaBO.atualizarBO(empresa);
            return Response.ok("Empresa atualizada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar empresa: " + e.getMessage()).build();
        }
    }

    // Deletar
    @DELETE
    @Path("/{id}")
    public Response deletarEmpresaRs(@PathParam("id") int id) {
        try {
            empresaBO.deletarBO(id);
            return Response.ok("Empresa deletada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao deletar empresa: " + e.getMessage()).build();
        }
    }

    // Selecionar Todos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarTodosRs() {
        try {
            List<Empresa> empresas = empresaBO.selecionarTodosBO();
            return Response.ok(empresas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar empresas: " + e.getMessage()).build();
        }
    }

    // Selecionar por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarRs(@PathParam("id") int id) {
        try {
            Empresa empresa = empresaBO.selecionarPorIdBO(id);
            return Response.ok(empresa).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Empresa não encontrada.").build();
        }
    }
}
