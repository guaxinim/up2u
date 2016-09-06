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
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
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
        context = DSL.using(dataSource, SQLDialect.POSTGRES_9_3);
        Usuario usuario = context.select()
                .from(USUARIO)
                .where(USUARIO.CODIGOUSUARIO.equal(id))
                .fetchAny()
                .into(Usuario.class);
        return usuario;
    }

    @Override
    public void removerUsuario(Integer codigoUsuario) {
        context = DSL.using(dataSource, SQLDialect.POSTGRES_9_3);
        context.delete(USUARIO)
                .where(USUARIO.CODIGOUSUARIO.equal(codigoUsuario))
                .execute();
    }


    @Override
    public List<Usuario> listarUsuarios() {
        //Configuration configuration = JAXB.unmarshal(new File("jooq.xml"), Configuration.class);
        context = DSL.using(dataSource, SQLDialect.POSTGRES_9_3);
        List<Usuario> usuarios = context.select()
                .from(USUARIO)
                .fetch()
                .map(getRecordMapper());
        return usuarios;
    }

    RecordMapper getRecordMapper() {
        return new RecordMapper() {
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
    }

}
