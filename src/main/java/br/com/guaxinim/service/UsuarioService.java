package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.util.maven.example.tables.records.UsuarioRecord;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import static org.jooq.util.maven.example.Tables.USUARIO;

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
    public List<Usuario> getUsuarios() {
        return entityManager.createNamedQuery("Usuario.findAll").getResultList();
    }
    public void removerUsuario(Integer codigoUsuario) {
        Usuario usuario = entityManager.find(Usuario.class, codigoUsuario);
        entityManager.remove(usuario);
    }


    public List<Usuario> listaUsuarios() {
        //Configuration configuration = JAXB.unmarshal(new File("jooq.xml"), Configuration.class);
        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES_9_3);
        RecordMapper rec = new RecordMapper() {
            @Override
            public Usuario map(Record record) {
                UsuarioRecord r = (UsuarioRecord) record;
                Usuario u = new Usuario();
                u.setCodigoUsuario(r.getCodigousuario());
                u.setNome(r.getNome());
                u.setCpf(r.getCpf());
                u.setNascimento(r.getNascimento());
                u.setObservacao(r.getObservacao());
                u.setTelefone(r.getTelefone());
                return u;
            }
        };

        List<Usuario> usuarios =
            context.select()
                .from(USUARIO)
                .fetch()
                .map(rec);
                //.map(rec);
                /*nativeQuery(entityManager,
                        DSL.using(configuration)
                                .select()
                                .from(UsuarioRecord.as("a"))
                        , Usuario.class);*/
        usuarios.forEach(usuario -> {
            System.out.println(USUARIO.NOME + " - ID: " + USUARIO.CPF);
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
