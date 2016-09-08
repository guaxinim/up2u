package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;
import br.com.guaxinim.setup.JooqContextProducer;
import org.jooq.DSLContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

import static org.jooq.util.maven.example.Tables.USUARIO;

@Stateless
public class UsuarioJooqService implements UsuarioService {

    static Logger logger = Logger.getLogger(UsuarioJPAService.class.getName());

    @Inject @JooqContextProducer
    DSLContext context;

    @Override
    public void inserirUsuario(Usuario u) {
        context.insertInto(USUARIO)
                .set(USUARIO.CODIGOUSUARIO, u.getCodigoUsuario())
                .set(USUARIO.NOME, u.getNome())
                .set(USUARIO.CPF, u.getCpf())
                .set(USUARIO.TELEFONE, u.getTelefone())
                .execute();
        logger.fine("Usuario inserido");
    }

    @Override
    public Usuario obterUsuario(Integer codigoUsuario) {
        Usuario usuario = context.select()
                .from(USUARIO)
                .where(USUARIO.CODIGOUSUARIO.equal(codigoUsuario))
                .fetchAny()
                .into(Usuario.class);
        logger.fine("Usuario retornado");
        return usuario;
    }

    @Override
    public void removerUsuario(Integer codigoUsuario) {
        context.delete(USUARIO)
                .where(USUARIO.CODIGOUSUARIO.equal(codigoUsuario))
                .execute();
        logger.fine("Usuario excluido");
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = context.select()
                .from(USUARIO)
                .fetch()
                .into(Usuario.class);
        logger.fine("Usuarios obtidos");
        return usuarios;
    }

}
