package com.voltiosx.wavescore.models;

public class RiderFixtureItem extends FixtureItem {

    private int numberHeat;
    private String name;
    private int tshirtcolor;
    private int colortextcontrast;

    public RiderFixtureItem(int numberHeat, String name, int colortextcontrast, int tshirtcolor){
        this.setNumberHeat(numberHeat);
        this.setName(name);
        this.setColortextcontrast(colortextcontrast);
        this.setTshirtcolor(tshirtcolor);
    }

    public int getNumberHeat() { return numberHeat; }
    public void setNumberHeat(int numberHeat) {
        this.numberHeat = numberHeat;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getTshirtcolor() {
        return tshirtcolor;
    }
    public void setTshirtcolor(int tshirtcolor) {
        this.tshirtcolor = tshirtcolor;
    }

    public int getColortextcontrast() {
        return colortextcontrast;
    }
    public void setColortextcontrast(int colortextcontrast) {
        this.colortextcontrast = colortextcontrast;
    }

    @Override
    public int getType() { return TYPE_RIDER; }

}