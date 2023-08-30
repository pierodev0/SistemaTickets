package com.example.sistemadetikets.entidades;

import java.io.Serializable;

public class TicketLayout implements Serializable {
    private int id;
    private String nombreSolicitud;
    private String Descripcion;
    private String estado;

    public TicketLayout(){

    }

    public TicketLayout(int id, String nombreSolicitud, String descripcion, String estado) {
        this.id = id;
        this.nombreSolicitud = nombreSolicitud;
        Descripcion = descripcion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombreSolicitud() {
        return nombreSolicitud;
    }

    public void setNombreSolicitud(String nombreSolicitud) {
        this.nombreSolicitud = nombreSolicitud;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
