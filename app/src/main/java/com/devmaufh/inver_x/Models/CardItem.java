package com.devmaufh.inver_x.Models;

public class CardItem {
    private String id,nombre,descripcion,giro,vision;
    private String url;


    public CardItem(String id, String nombre, String descripcion, String giro, String vision,String url) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.giro = giro;
        this.vision = vision;
        this.url=url;
    }
    public CardItem(){}
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
    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){
        return this.url;
    }



}
