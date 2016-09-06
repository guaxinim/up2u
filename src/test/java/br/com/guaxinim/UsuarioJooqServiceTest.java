package br.com.guaxinim;

import br.com.guaxinim.entities.Usuario;
import br.com.guaxinim.service.UsuarioJooqService;
import br.com.guaxinim.service.UsuarioService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.Date;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class UsuarioJooqServiceTest {

    Logger log = Logger.getLogger(UsuarioJooqServiceTest.class.getName());

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(UsuarioJooqService.class)
                .addClass(Usuario.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    UsuarioService usuarioService;

    static Integer codigoUsuario;

    @Before
    @InSequence(1)
    public void inicio() {
        log.info("Iniciando testes");
        Assert.assertNotNull(usuarioService);
    }

    @Test
    @InSequence(2)
    public void testInsertUsuario() {
        log.info("Test inserirUsuario()");
        Usuario u1 = new Usuario();
        u1.setNome("Arquillian User");
        u1.setCpf("12345678901");
        u1.setNascimento(new Date());
        u1.setTelefone("6181185744");
        u1.setObservacao("teste teste teste");
        usuarioService.inserirUsuario(u1);
        Assert.assertNotNull(u1.getCodigoUsuario());
        codigoUsuario = u1.getCodigoUsuario();
        log.info("Usuario " + codigoUsuario + " inserted");
    }

    @Test
    @InSequence(3)
    public void testValidationUsuario() {
        log.info("Test inserirUsuario()");
        Usuario u1 = new Usuario();
        u1.setTelefone("6181185744");
        u1.setObservacao("teste teste teste");
        try {
            usuarioService.inserirUsuario(u1);
            Assert.fail("ContraintViolationException esperada");
        } catch (Exception ex) {
            Assert.assertTrue(getRootCause(ex).toString().contains("Validation failed"));

        }
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null && cause != throwable) {
            throwable = cause;
        }
        return throwable;
    }

    @Test
    @InSequence(4)
    public void testGetUsuario() {
        log.info("Test obterUsuario()");
        Assert.assertNotNull(codigoUsuario);
        Usuario u2 = usuarioService.obterUsuario(codigoUsuario);
        Assert.assertNotNull(u2);
        Assert.assertEquals(u2.getNome(), "Arquillian User");
    }

}
