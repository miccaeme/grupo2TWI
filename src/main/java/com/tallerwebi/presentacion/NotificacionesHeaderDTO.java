package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Notificacion;

import java.util.List;

public class NotificacionesHeaderDTO {
    private List<Notificacion> notificaciones;
    private int cantNoLeidas;
    private String nickname;


    public NotificacionesHeaderDTO() {}

    // Constructor lleno
    public NotificacionesHeaderDTO(List<Notificacion> notificaciones, int cantNoLeidas, String nickname) {
        this.notificaciones = notificaciones;
        this.cantNoLeidas = cantNoLeidas;
        this.nickname = nickname;
    }


    public List<Notificacion> getNotificaciones() { return notificaciones; }
    public void setNotificaciones(List<Notificacion> notificaciones) { this.notificaciones = notificaciones; }

    public int getCantNoLeidas() { return cantNoLeidas; }
    public void setCantNoLeidas(int cantNoLeidas) { this.cantNoLeidas = cantNoLeidas; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
