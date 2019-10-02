package com.example.nolti.wavescore.models;

import java.util.ArrayList;

public class RiderHeat {
    private int id, position;
    private String ranking, name, hometown, categoria, heatstatus, latestatus, colorlycra;
    private ArrayList<Double> wavescores;
    private Double totalscore;

    public RiderHeat(int id, int position, String ranking, String name, String hometown, String categoria, String heatstatus, String latestatus, String colorlycra, ArrayList<Double> wavescores, Double totalscore) {
        this.id = id;
        this.position = position;
        this.ranking = ranking;
        this.name = name;
        this.hometown = hometown;
        this.categoria = categoria;
        this.heatstatus = heatstatus;
        this.latestatus = latestatus;
        this.colorlycra = colorlycra;
        this.wavescores = wavescores;
        this.totalscore = totalscore;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public String getRanking() {
        return ranking;
    }

    public String getName() {
        return name;
    }

    public String getHometown() {
        return hometown;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getHeatstatus() {
        return heatstatus;
    }

    public String getLatestatus() {
        return latestatus;
    }

    public String getColorlycra() {
        return colorlycra;
    }

    public ArrayList<Double> getWavescores() {
        return wavescores;
    }

    public Double getTotalscore() {
        return totalscore;
    }
}
