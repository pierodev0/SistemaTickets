package com.example.sistemadetikets.entidades;

import java.io.Serializable;

public class Ticket implements Serializable {
    private Integer id;
    private Integer id_usuario;
    private String fechaCreacion;
    private String tipoTicket;
    private String Descripcion;

    private String estadoTicket;

    public Ticket() {

    }

    public Ticket(Integer id, Integer id_usuario, String fechaCreacion, String tipoTicket, String descripcion, String estadoTicket) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.fechaCreacion = fechaCreacion;
        this.tipoTicket = tipoTicket;
        Descripcion = descripcion;
        this.estadoTicket = estadoTicket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTipoTicket() {
        return tipoTicket;
    }

    public void setTipoTicket(String tipoTicket) {
        this.tipoTicket = tipoTicket;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getEstadoTicket() {
        return estadoTicket;
    }

    public void setEstadoTicket(String estadoTicket) {
        this.estadoTicket = estadoTicket;
    }
}
