package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Enums.Deporte;

import javax.persistence.*;

@Entity
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Deporte deporte;

    @ManyToOne
    private Usuario creador;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public void setCantidadMaximaSlots(int cupoMaximo) {
    }

    public int getCantidadMaximaSlots() {
        if (this.deporte != null) {
            return this.deporte.getSlots();
        }
        return 0;
    }
}