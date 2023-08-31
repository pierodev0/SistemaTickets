package com.example.sistemadetikets.entidades;

public class Contacto {

    private Integer id;

    private String nombre;
    private Integer numeros_ticket;
    private String razon_social;

    public Contacto() {

    }

    public Contacto(Integer id, String nombre, Integer numeros_ticket, String razon_social) {
        this.id = id;
        this.nombre = nombre;
        this.numeros_ticket = numeros_ticket;
        this.razon_social = razon_social;
    }

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

    public Integer getNumeros_ticket() {
        return numeros_ticket;
    }

    public void setNumeros_ticket(Integer numeros_ticket) {
        this.numeros_ticket = numeros_ticket;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }
}
