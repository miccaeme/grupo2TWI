package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.Posicion;
import javax.persistence.*;

@Entity
public class Jugador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String apellido;

  @Column(unique = true, nullable = false)
  private Integer dni;

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }


  public Integer getDni() {
    return dni;
  }

  public void setDni(Integer dni) {
    this.dni = dni;
  }

}
