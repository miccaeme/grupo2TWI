package com.tallerwebi.dominio;

import com.tallerwebi.dominio.contratos.RepositorioUsuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import javax.transaction.Transactional;

import com.tallerwebi.dominio.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

  private RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioLoginImpl(RepositorioUsuario repositorioUsuario) {
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public Usuario consultarUsuario(String email, String password) {
    return repositorioUsuario.buscarUsuario(email, password);
  }

  @Override
  public void registrar(Usuario usuario) throws UsuarioExistente {
    Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(
      usuario.getEmail(),
      usuario.getPassword()
    );
    if (usuarioEncontrado != null) {
      throw new UsuarioExistente();
    }
    Jugador jugador = usuario.getJugador();
    if (jugador != null) {
      jugador.setUsuario(usuario);
      usuario.setJugador(jugador);
    }
    usuario.setRol("JUGADOR");
    repositorioUsuario.guardar(usuario);
  }

  @Override
  public Usuario buscarUsuarioPorId(Long id) {
    return repositorioUsuario.buscarUsuarioPorId(id);
  }

  @Override
  public Usuario buscarUsuarioPorNickname(String nickname) {
    // 🌟 Súper limpio: El servicio le pide al repositorio el usuario por nick
    return repositorioUsuario.buscarPorNickname(nickname);
  }
}




