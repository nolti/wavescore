package com.voltiosx.nolti.wavescore.models;

import android.support.annotation.NonNull;

public class HeaderFixtureItem extends FixtureItem {

	@NonNull
	private int num;
    @NonNull
    private String titulo;

	public HeaderFixtureItem(int num, @NonNull String titulo) {
	    this.num = num;
		this.titulo = titulo;
	}

    @NonNull
    public int getNum() {
        return num;
    }

	@NonNull
	public String getTitulo() {
		return titulo;
	}

	// here getters and setters
	// for title and so on, built
	// using date

	@Override
	public int getType() { return TYPE_HEADER; }

}