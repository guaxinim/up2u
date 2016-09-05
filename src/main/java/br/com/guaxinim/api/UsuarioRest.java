package br.com.guaxinim.api;
import br.com.guaxinim.entities.Usuario;
import br.com.guaxinim.service.UsuarioService;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/")
public class UsuarioRest {

    Logger log = Logger.getLogger(UsuarioRest.class.getName());

    @EJB
    UsuarioService usuarioService;

    @GET
    @Path("usuario/{id}")
    @Produces("application/json")
    public Usuario getUsuario(@PathParam("id") String id, @Context final HttpServletResponse response) {
        Integer param = Integer.valueOf(id);
        Usuario usuario = usuarioService.getUsuario(param);
        return usuario;
    }

    @GET
    @Path("usuarios")
    @Produces("application/json")
    public List<Usuario> getUsuarios(@Context final HttpServletResponse response) {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return usuarios;
    }

    @POST
    @Path("usuario")
    @Consumes("application/json")
    public Response inserirUsuario(Usuario usuario, @Context final HttpServletResponse response) {
        usuario.setCodigoUsuario(null);
        usuarioService.inserirUsuario(usuario);
        return Response.status(Response.Status.CREATED).entity("Usuario inserido").build();
    }

    @DELETE
    @Path("usuario/{id}")
    @Produces("application/json")
    public Response removerUsuario(@PathParam("id") String id, @Context final HttpServletResponse response) {
        Integer param = Integer.valueOf(id);
        usuarioService.removerUsuario(param);
        return Response.status(Response.Status.OK).entity("Usuario inserido").build();
    }

    @GET
    @Path("lista")
    @Produces("application/json")
    public List<Usuario> getListaUsuarios(@Context final HttpServletResponse response) {
        List<Usuario> usuarios = usuarioService.listaUsuarios();
        //List<Usuario> usuarios = usuarioService.getUsuarios();
        return usuarios;
    }
}
