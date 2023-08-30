package com.example.sistemadetikets.entidades;

import java.io.Serializable;

public class Ticket implements Serializable {
    private Integer id;
    private Integer id_usuario;
    private String fechaCreacion;
    private Integer id_estado;
    private Integer id_solicitud;
    private String Descripcion;
    private String solucion;

    public Ticket() {

    }

    public Ticket(Integer id, Integer id_usuario, String fechaCreacion, Integer id_estado, Integer id_solicitud, String descripcion, String solucion) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.fechaCreacion = fechaCreacion;
        this.id_estado = id_estado;
        this.id_solicitud = id_solicitud;
        Descripcion = descripcion;
        this.solucion = solucion;
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

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }
}
