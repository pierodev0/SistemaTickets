package com.example.sistemadetikets.entidades;

public class Contacto {

    private Integer id;
    private Integer id_usuario;
    private Integer numeros_ticket;
    private String razon_social;

    public Contacto() {

    }

    public Contacto(Integer id, Integer id_usuario, Integer numeros_ticket, String razon_social) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.numeros_ticket = numeros_ticket;
        this.razon_social = razon_social;
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
