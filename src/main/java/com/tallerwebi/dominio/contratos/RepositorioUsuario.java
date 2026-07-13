package com.tallerwebi.dominio.contratos;

import com.tallerwebi.dominio.Usuario;

public interface RepositorioUsuario {
  Usuario buscarUsuario(String email, String password);
  void guardar(Usuario usuario);
  Usuario buscar(String email);
  void modificar(Usuario usuario);
  Usuario buscarUsuarioPorId(Long id);


    Usuario buscarPorNickname(String nickname);
}
