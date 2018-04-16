package com.voltiosx.nolti.wavescore.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ColorWaveScore implements Parcelable {

    private int colorTexto;
    private int colorLycra;

    public int getColorTexto() {
        return colorTexto;
    }
    public void setColorTexto(int colorTexto) {
        this.colorTexto = colorTexto;
    }

    public int getColorLycra() {
        return colorLycra;
    }
    public void setColorLycra(int colorLycra) {
        this.colorLycra = colorLycra;
    }

    public ColorWaveScore(int colorTexto, int colorLycra) {
        this.setColorTexto(colorTexto);
        this.setColorLycra(colorLycra);
    }

    protected ColorWaveScore(Parcel in) {
        this.colorTexto = in.readInt();
        this.colorLycra = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(colorTexto);
        dest.writeInt(colorLycra);
    }

    public static final Creator<ColorWaveScore> CREATOR = new Creator<ColorWaveScore>() {
        public ColorWaveScore createFromParcel(Parcel in) {
            return new ColorWaveScore(in);
        }

        @Override
        public ColorWaveScore[] newArray(int size) {
            return new ColorWaveScore[size];
        }
    };

}