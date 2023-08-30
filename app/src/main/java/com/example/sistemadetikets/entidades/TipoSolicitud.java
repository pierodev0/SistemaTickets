package com.example.sistemadetikets.entidades;

public class TipoSolicitud {
    private Integer id;
    private String nombre;
    private int id_empleado;

    public TipoSolicitud(Integer id, String nombre, int id_empleado) {
        this.id = id;
        this.nombre = nombre;
        this.id_empleado = id_empleado;
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
    public Integer getIdEmpleado() {
        return id_empleado;
    }

    public void setIdEmpleado(Integer id_empleado) {
        this.id_empleado = id_empleado;
    }
}
