package br.com.solara.resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
    public Response deletarEmpresa(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
        empresaBO.deletarBO(id);
        return Response.ok().build();
    }

    // Consultar todas (GET)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEmpresas() throws ClassNotFoundException, SQLException {
        List<Empresa> empresas = empresaBO.selecionarBO();
        return Response.ok(empresas).build();
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
