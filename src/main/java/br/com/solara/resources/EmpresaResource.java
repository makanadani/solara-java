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

    // Inserir empresa
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarEmpresa(Empresa empresa) {
        try {
            empresaBO.inserirBO(empresa);
            return Response.status(Response.Status.CREATED).entity("Empresa criada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar empresa: " + e.getMessage()).build();
        }
    }

    // Atualizar empresa
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarEmpresa(@PathParam("id") int id, Empresa empresa) {
        try {
            empresa.setIdEmpresa(id);
            empresaBO.atualizarBO(empresa);
            return Response.ok("Empresa atualizada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar empresa: " + e.getMessage()).build();
        }
    }

    // Deletar empresa
    @DELETE
    @Path("/{id}")
    public Response deletarEmpresa(@PathParam("id") int id) {
        try {
            empresaBO.deletarBO(id);
            return Response.ok("Empresa deletada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao deletar empresa: " + e.getMessage()).build();
        }
    }

    // Consultar todas as empresas
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEmpresas() {
        try {
            List<Empresa> empresas = empresaBO.selecionarTodosBO();
            return Response.ok(empresas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar empresas: " + e.getMessage()).build();
        }
    }

    // Consultar empresa por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEmpresaPorId(@PathParam("id") int id) {
        try {
            Empresa empresa = empresaBO.selecionarPorIdBO(id);
            return Response.ok(empresa).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Empresa não encontrada.").build();
        }
    }
}
