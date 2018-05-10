package com.voltiosx.nolti.wavescore.clases;

import java.util.Date;

public class Fixture {

    private int numRiders; //Son el numero de Inscriptos en una categoria
    private int numAgrupacion; //Modo en que se prefiere agrupar los Heats
    private String categoria; //Categoria a la cual pertenece el Fixture
    private String torneo; //Nombre del Torneo
    private String etapa; //Nombre de la Etapa
    private String instancia; //Nombre de la instancia del Heat
    private Date fecha; //Fecha en que se genera el fixture
    private String beachMarshal; //Nombre del Beach Marshal

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

    /* METODOS UTILES PARA MI FIXTURE */
    // Metodo que devuelve el numero con el que se debe agrupar los Heats del Fixture
    public int heatGroupQuery(int numR) {
        int n = numR;
        if (n>1){
            //entro el 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17 infinito
            if (n%2==0) {
                //es par
                //entro el 2,4,6,8,10,12,14,16
                if (n!=2) {
                    //entro el 4,6,8,10,12,14,16
                    if (n%4==0){
                        //entro el 4,8,12,16
                        return 4;
                    } else {
                        //entro el 6,10,14
                        if (n%3==0){
                            //entro el 6
                            return 3;
                        } else {
                            //entro el 10,14
                            return 5;
                        }
                    }
                } else {
                    //entro el 2
                    return 2; //final de 2
                }
            } else {
                //es impar
                //entro el 3,5,7,9,11,13,15,17
                if (n%3==0) {
                    //entro el 3,9,15
                    if (n!=3) {
                        //entro el 9,15
                        if (n!=9){
                            //entro el 15
                            return 4;
                        } else {
                            //entro el 9
                            return 3;
                        }
                    } else {
                        //entro el 3
                        return 3; //final de 3
                    }
                } else if (n%5==0) {
                    //entro el 5
                    if (n!=5) {
                        //entro el 15
                        return 4;
                    } else {
                        //entro el 5
                        return 5; //final de 5 directa
                    }
                } else {
                    //entro el 11,13,
                    return 4;
                }
            }
        } else {
            //return "Solo hay un inscripto, por lo tanto no podras competir";
            return 1;
        }
    }
}
