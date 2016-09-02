package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.util.maven.example.tables.records.UsuarioRecord;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.xml.bind.JAXB;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsuarioService {

    @PersistenceContext
    EntityManager entityManager;

    @Resource(lookup="java:jboss/datasources/jooqDS")
    DataSource dataSource;

    @Produces
    public DSLContext jooq() {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Inject
    private DSLContext ctx;

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

    public static <E> List<E> nativeQuery(EntityManager em, org.jooq.Query query, Class<E> type) {
        Query result = em.createNativeQuery(query.getSQL(), type);
        List<Object> values = query.getBindValues();
        for (int i = 0; i < values.size(); i++) {
            result.setParameter(i + 1, values.get(i));
        }
        return result.getResultList();
    }

    public List<Usuario> listaUsuarios() {
        Configuration configuration = JAXB.unmarshal(new File("jooq.xml"), Configuration.class);
        List<Usuario> usuarios =
                nativeQuery(entityManager,
                        DSL.using(configuration)
                                .select()
                                .from(org.jooq.util.maven.example.tables.Usuario)
                        , Usuario.class);
        usuarios.forEach(usuario -> {
            System.out.println(usuario.getNome() + " " + usuario.getCpf() + " wrote");
            /*books.forEach(book -> {
                //System.out.println("  " + book.title);
            });*/
        });
        return usuarios;
    }

    public List<Usuario> obtemUsuarios() {

        //ctx.select()
        return new ArrayList<Usuario>();
    }

}
