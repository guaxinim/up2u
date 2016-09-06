package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.util.maven.example.tables.records.UsuarioRecord;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

import static org.jooq.util.maven.example.Tables.USUARIO;

@Stateless
public class UsuarioJooqService implements UsuarioService {

    static Logger logger = Logger.getLogger(UsuarioJPAService.class.getName());

    public UsuarioJooqService() {
        logger.info("UsuarioJooqService");
    }

    @PersistenceContext
    EntityManager entityManager;

    @Resource(lookup="java:jboss/datasources/jooqDS")
    DataSource dataSource;

    DSLContext context;

    @Override
    public void inserirUsuario(Usuario u) {
        context = DSL.using(dataSource, SQLDialect.POSTGRES_9_3);
        System.out.println(u);
        context.insertInto(USUARIO)
                .set(USUARIO.CODIGOUSUARIO, u.getCodigoUsuario())
                .set(USUARIO.NOME, u.getNome())
                .set(USUARIO.CPF, u.getCpf())
                .set(USUARIO.TELEFONE, u.getTelefone())
                .execute();
        //entityManager.persist(u);
    }
    @Override
    public Usuario obterUsuario(Integer id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public void removerUsuario(Integer codigoUsuario) {
        Usuario usuario = entityManager.find(Usuario.class, codigoUsuario);
        entityManager.remove(usuario);
    }


    @Override
    public List<Usuario> listarUsuarios() {
        //Configuration configuration = JAXB.unmarshal(new File("jooq.xml"), Configuration.class);
        context = DSL.using(dataSource, SQLDialect.POSTGRES_9_3);
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

        List<Usuario> usuarios = context.select()
                .from(USUARIO)
                .fetch()
                .map(rec);
        return usuarios;
    }

}
