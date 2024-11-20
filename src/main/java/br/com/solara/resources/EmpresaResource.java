package br.com.solara.resources;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.solara.model.bo.EmpresaBO;
import br.com.solara.model.vo.Empresa;

@Path("/empresas")
public class EmpresaResource {

    private EmpresaBO empresaBO = new EmpresaBO();

    // Inserir (POST)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarEmpresa(Empresa empresa, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        empresaBO.inserirBO(empresa);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(empresa.getIdEmpresa()));
        return Response.created(builder.build()).build();
    }

    // Atualizar (PUT)
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarEmpresa(@PathParam("id") int id, Empresa empresa) throws ClassNotFoundException, SQLException {
        empresa.setIdEmpresa(id);
        empresaBO.atualizarBO(empresa);
        return Response.ok().build();
    }

    // Deletar (DELETE)
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletarEmpresa(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
        empresaBO.deletarBO(id);
        return Response.ok().build();
    }

    // Consultar todas (GET)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Empresa> listarEmpresas() throws ClassNotFoundException, SQLException {
        return (ArrayList<Empresa>) empresaBO.selecionarBO();
    }

    // Consultar por ID (GET)
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEmpresaPorId(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
        Empresa empresa = empresaBO.buscarEmpresaPorId(id);
        if (empresa == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Empresa não encontrada.")
                    .build();
        }
        return Response.ok(empresa).build();
    }
}
