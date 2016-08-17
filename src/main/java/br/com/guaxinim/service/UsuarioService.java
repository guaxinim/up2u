package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class UsuarioService {

    @PersistenceContext
    EntityManager entityManager;

    public void inserirUsuario(Usuario u) {
        entityManager.persist(u);
    }
    public Usuario getUsuario(Integer id) {
        return entityManager.find(Usuario.class, id);
    }
    public List<Usuario> getUsuarios() { return entityManager.createNamedQuery("Usuario.findAll").getResultList();}
    public void removerUsuario(Integer codigoUsuario) {
        Usuario usuario = entityManager.find(Usuario.class, codigoUsuario);
        entityManager.remove(usuario);
    }

}
