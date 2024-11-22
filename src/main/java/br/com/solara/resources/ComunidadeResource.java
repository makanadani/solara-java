package br.com.solara.resources;

import br.com.solara.model.bo.ComunidadeBO;
import br.com.solara.model.vo.Comunidade;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/comunidades")
public class ComunidadeResource {

    private final ComunidadeBO comunidadeBO;

    public ComunidadeResource() throws SQLException, ClassNotFoundException {
        this.comunidadeBO = new ComunidadeBO();
    }

    // Inserir comunidade
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarComunidade(Comunidade comunidade) {
        try {
            comunidadeBO.inserirBO(comunidade);
            return Response.status(Response.Status.CREATED).entity("Comunidade criada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar comunidade: " + e.getMessage()).build();
        }
    }

    // Atualizar comunidade
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarComunidade(@PathParam("id") int id, Comunidade comunidade) {
        try {
            comunidade.setIdComunidade(id);
            comunidadeBO.atualizarBO(comunidade);
            return Response.ok("Comunidade atualizada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar comunidade: " + e.getMessage()).build();
        }
    }

    // Deletar comunidade
    @DELETE
    @Path("/{id}")
    public Response deletarComunidade(@PathParam("id") int id) {
        try {
            comunidadeBO.deletarBO(id);
            return Response.ok("Comunidade deletada com sucesso!").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Comunidade não encontrada.").build();
        }
    }

    // Consultar todas as comunidades
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarComunidades() {
        try {
            List<Comunidade> comunidades = comunidadeBO.selecionarTodosBO();
            return Response.ok(comunidades).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar comunidades: " + e.getMessage()).build();
        }
    }

    // Consultar comunidade por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarComunidadePorId(@PathParam("id") int id) {
        try {
            Comunidade comunidade = comunidadeBO.selecionarBO(id);
            return Response.ok(comunidade).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Comunidade não encontrada.").build();
        }
    }
}
