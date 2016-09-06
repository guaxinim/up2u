package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;

import java.util.List;

/**
 * Created by elvis on 6/09/2016.
 */
public interface UsuarioService {

    void inserirUsuario(Usuario u);

    Usuario obterUsuario(Integer id);

    void removerUsuario(Integer codigoUsuario);

    List<Usuario> listarUsuarios();
}
