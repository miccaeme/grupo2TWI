package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import javax.persistence.*;

@Entity
public class Jugador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nick;

  @OneToOne
  private Usuario usuario;


  public Long getId() {
    return id;
  }
  public void setId(Long id){
    this.id = id;
  }

  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}
