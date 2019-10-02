package com.example.nolti.wavescore.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Wave implements Parcelable {

    private int position;
    private double score;

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    public Wave(int position, Double score) {
        this.setPosition(position);
        this.setScore(score);
    }

    protected Wave(Parcel in) {
        this.position = in.readInt();
        this.score = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeDouble(score);
    }

    public static final Creator<Wave> CREATOR = new Creator<Wave>() {
        public Wave createFromParcel(Parcel in) {
            return new Wave(in);
        }

        @Override
        public Wave[] newArray(int size) {
            return new Wave[size];
        }
    };

    /*@Override
    public int compareTo(@NonNull Wave w) {
        if (score<w.score) {
            return -1;
        }
        if (score>w.score){
            return 1;
        }
        return 0;
    }*/
}