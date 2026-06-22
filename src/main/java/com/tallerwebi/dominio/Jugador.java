package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import javax.persistence.*;

@Entity
public class Jugador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nickname;

  @OneToOne
  private Usuario usuario;


  public Long getId() {
    return id;
  }
  public void setId(Long id){
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}
