package com.devmaufh.inver_x.Models;

public class CardItem {
    private String id,nombre,descripcion,giro,vision;



    public CardItem(String id, String nombre, String descripcion, String giro, String vision) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.giro = giro;
        this.vision = vision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getVision() {
        return vision;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setVision(String vision) {
        this.vision = vision;
    }
}
