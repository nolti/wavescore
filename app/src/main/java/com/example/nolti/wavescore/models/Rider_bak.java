package com.example.nolti.wavescore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/* implementar Serializable me permite enviar este objeto como parametro */
public class Rider_bak implements Serializable {
    @SerializedName("posicion")
    private int position;
    private String nombre, categoria;

    public Rider_bak(int position, String nombre, String categoria) {
        this.setPosition(position);
        this.setNombre(nombre);
        this.setCategoria(categoria);
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}