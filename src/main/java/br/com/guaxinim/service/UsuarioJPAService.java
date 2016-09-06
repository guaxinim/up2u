package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Alternative
public class UsuarioJPAService implements UsuarioService {

    static Logger logger = Logger.getLogger(UsuarioJPAService.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public void inserirUsuario(Usuario u) {
        entityManager.persist(u);
    }

    public Usuario obterUsuario(Integer id) {
        return entityManager.find(Usuario.class, id);
    }

    public List<Usuario> listarUsuarios() {
        return entityManager.createNamedQuery("Usuario.findAll").getResultList();
    }

    public void removerUsuario(Integer codigoUsuario) {
        Usuario usuario = entityManager.find(Usuario.class, codigoUsuario);
        entityManager.remove(usuario);
    }

}
