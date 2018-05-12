package com.voltiosx.nolti.wavescore.clases;

import java.util.Date;

public class Fixture {

    private int numRiders; // Numero de inscriptos en la categoria
    private int numAgrupacion; // Modo en que se prefiere agrupar los Heats
    private String categoria; // Categoria a la cual pertenece el Fixture
    private String torneo; // Nombre del Torneo
    private String etapa; // Nombre de la Etapa
    private String instancia; // Nombre de la instancia del Heat
    private Date fecha; // Fecha en que se genera el fixture
    private String beachMarshal; // Nombre del Beach Marshal

    public int getNumRiders() {
        return numRiders;
    }

    public void setNumRiders(int numRiders) {
        this.numRiders = numRiders;
    }

    public int getNumAgrupacion() {
        return numAgrupacion;
    }

    public void setNumAgrupacion(int numAgrupacion) {
        this.numAgrupacion = numAgrupacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTorneo() {
        return torneo;
    }

    public void setTorneo(String torneo) {
        this.torneo = torneo;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBeachMarshal() {
        return beachMarshal;
    }

    public void setBeachMarshal(String beachMarshal) {
        this.beachMarshal = beachMarshal;
    }

    /* METODOS PARA FIXTURE */

}
