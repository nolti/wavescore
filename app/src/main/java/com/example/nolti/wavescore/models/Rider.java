package com.example.nolti.wavescore.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.ArrayList;

public class Rider implements Comparable<Rider>, Parcelable {

    private int id;
    private int position;
    private String name;
    private String hometown;
    private String categoria;
    private ArrayList<Integer> colors;
    private ArrayList<Double> wavestaken;
    private ArrayList<Double> sortwavestaken;
    private ArrayList<Double> heatscores;
    private String heatstatus;

    public Rider(int id, int position, String name, String hometown, String categoria, ArrayList<Integer> colors, ArrayList<Double> wavestaken, ArrayList<Double> sortwavestaken, ArrayList<Double> heatscores, String heatstatus) {
        this.setId(id);
        this.setPosition(position);
        this.setName(name);
        this.setHometown(hometown);
        this.setCategoria(categoria);
        this.setColors(colors);
        this.setWavestaken(wavestaken);
        this.setSortwavestaken(sortwavestaken);
        this.setHeatscores(heatscores);
        this.setHeatstatus(heatstatus);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getHometown() {
        return hometown;
    }
    public void setHometown(String name) {
        this.hometown = name;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }
    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Double> getWavestaken() {
        return wavestaken;
    }
    public void setWavestaken(ArrayList<Double> wavestaken) {
        this.wavestaken = wavestaken;
    }

    public ArrayList<Double> getSortwavestaken() {
        return sortwavestaken;
    }
    public void setSortwavestaken(ArrayList<Double> sortwavestaken) { this.sortwavestaken = sortwavestaken; }

    public ArrayList<Double> getHeatscores() {
        return heatscores;
    }
    public void setHeatscores(ArrayList<Double> heatscores) {
        this.heatscores = heatscores;
    }

    public String getHeatstatus() {
        return heatstatus;
    }
    public void setHeatstatus(String heatstatus) {
        this.heatstatus = heatstatus;
    }

    protected Rider(Parcel in) {
        this.id = in.readInt();
        this.position = in.readInt();
        this.name = in.readString();
        this.hometown = in.readString();
        this.categoria = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(position);
        dest.writeString(name);
        dest.writeString(hometown);
        dest.writeString(categoria);
    }

    public static final Parcelable.Creator<Rider> CREATOR = new Parcelable.Creator<Rider>() {
        public Rider createFromParcel(Parcel in) {
            return new Rider(in);
        }
        @Override
        public Rider[] newArray(int size) {
            return new Rider[size];
        }
    };

    @Override
    public int compareTo(@NonNull Rider o) {
        // Ordena la posicion segun el Total del Score
        return this.getHeatscores().get(0).compareTo(o.getHeatscores().get(0));
        // Ordena la posicion segun el Tiebreak
        //return this.getHeatscores().get(12).compareTo(o.getHeatscores().get(12));
    }
}