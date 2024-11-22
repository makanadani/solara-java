package br.com.solara.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import br.com.solara.model.bo.EmpresaBO;
import br.com.solara.model.vo.Empresa;

@Path("/empresas")
public class EmpresaResource {

    private final EmpresaBO empresaBO;

    public EmpresaResource() throws SQLException, ClassNotFoundException {
        this.empresaBO = new EmpresaBO();
    }

    // Inserir (POST)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarEmpresa(Empresa empresa, @Context UriInfo uriInfo) {
        try {
            empresaBO.inserirBO(empresa);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(empresa.getIdEmpresa()));
            return Response.created(builder.build()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar a empresa: " + e.getMessage()).build();
        }
    }

    // Atualizar (PUT)
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarEmpresa(@PathParam("id") int id, Empresa empresa) {
        try {
            empresa.setIdEmpresa(id);
            empresaBO.atualizarBO(empresa);  // Atualizar empresa no banco de dados
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar a empresa: " + e.getMessage()).build();
        }
    }

    // Deletar (DELETE)
    @DELETE
    @Path("/{id}")
    public Response deletarEmpresa(@PathParam("id") int id) {
        try {
            // Verificar se a empresa existe antes de deletar
            Empresa empresa = empresaBO.selecionarPorIdBO(id);
            if (empresa == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Empresa não encontrada.").build();
            }
            empresaBO.deletarBO(id);  // Deletar a empresa no banco de dados
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao deletar a empresa: " + e.getMessage()).build();
        }
    }

    // Consultar todas as empresas (GET)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEmpresas() {
        try {
            List<Empresa> empresas = empresaBO.selecionarTodosBO();  // Selecionar todas as empresas
            return Response.ok(empresas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar empresas: " + e.getMessage()).build();
        }
    }

    // Consultar por ID (GET)
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEmpresaPorId(@PathParam("id") int id) {
        try {
            Empresa empresa = empresaBO.selecionarPorIdBO(id);  // Buscar empresa pelo ID
            if (empresa == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Empresa não encontrada.").build();
            }
            return Response.ok(empresa).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar empresa: " + e.getMessage()).build();
        }
    }
}
