package com.example.sistemadetikets.entidades;

public class TipoSolicitud {
    private Integer id;
    private String nombre;

    public TipoSolicitud(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoSolicitud(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
