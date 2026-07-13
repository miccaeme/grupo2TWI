package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {
  Usuario consultarUsuario(String email, String password);
  void registrar(Usuario usuario) throws UsuarioExistente;
  Usuario buscarUsuarioPorId(Long id);
  Usuario buscarUsuarioPorNickname(String nickname);
}
