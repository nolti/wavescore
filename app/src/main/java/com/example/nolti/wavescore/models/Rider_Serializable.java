package com.example.nolti.wavescore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/* implementar Serializable me permite enviar este objeto como parametro */
public class Rider_Serializable implements Parcelable,Serializable {

    @SerializedName("posicion")
    private int position;
    private String nombre;
    private String categoria;

    public Rider_Serializable(int position, String nombre, String categoria) {
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

    protected Rider_Serializable(Parcel in) {
        position = in.readInt();
        nombre = in.readString();
        categoria = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeString(nombre);
        dest.writeString(categoria);
    }

    private void readFromParcel(Parcel in) {
    }

    public static final Creator<Rider_Serializable> CREATOR = new Creator<Rider_Serializable>() {
        public Rider_Serializable createFromParcel(Parcel in) {
            return new Rider_Serializable(in);
        }

        @Override
        public Rider_Serializable[] newArray(int size) {
            return new Rider_Serializable[size];
        }
    };
}