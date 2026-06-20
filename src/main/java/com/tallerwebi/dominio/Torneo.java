package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Deporte;
import com.tallerwebi.dominio.Enums.TipoDeTorneo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Enumerated(EnumType.STRING)
    private Deporte deporte;
    private String lugar;
    private Integer cantidadEquipos;
    private String formato;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDeInicio;
    @Enumerated(EnumType.STRING)
    private TipoDeTorneo tipoDeTorneo;
    private Double precio;
    private String descripcion;
    private Boolean fixtureGenerado = false;
    @ManyToOne
    private Usuario creador;



    public Torneo() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public TipoDeTorneo getTipoDeTorneo() {
        return tipoDeTorneo;
    }

    public void setTipoDeTorneo(TipoDeTorneo tipoDeTorneo) {
        this.tipoDeTorneo = tipoDeTorneo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Integer getCantidadEquipos() {
        return cantidadEquipos;
    }

    public void setCantidadEquipos(Integer cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public LocalDate getFechaDeInicio() {
        return fechaDeInicio;
    }

    public void setFechaDeInicio(LocalDate fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Boolean getFixtureGenerado() {
        return fixtureGenerado;
    }

    public void setFixtureGenerado(Boolean fixtureGenerado) {
        this.fixtureGenerado = fixtureGenerado;
    }
}
