package com.example.sistemadetikets.entidades;

import java.io.Serializable;

public class TicketLayout implements Serializable {
    private int id;
    private String Titulo;
    private String Descripcion;
    private String estado;

    public TicketLayout(int id, String titulo, String descripcion, String estado) {
        this.id = id;
        Titulo = titulo;
        Descripcion = descripcion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
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
