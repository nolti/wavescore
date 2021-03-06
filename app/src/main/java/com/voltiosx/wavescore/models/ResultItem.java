package com.voltiosx.wavescore.models;

import java.util.ArrayList;

public class ResultItem extends ListItem {

	private int id;
	private int position;
	private String name;
	private String hometown;
	private int ranking;
	private Double score;
	private ArrayList<Integer> colors;
	private ArrayList<Double> wavestaken;

	public ResultItem(int id, int position, String name, String hometown, int ranking, Double score, ArrayList<Integer> colors, ArrayList<Double> wavestaken) {
		this.setId(id);
		this.setPosition(position);
		this.setName(name);
		this.setHometown(hometown);
		this.setRanking(ranking);
		this.setColors(colors);
		this.setWavestaken(wavestaken);
		this.setScore(score);
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

	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}

	public ArrayList<Integer> getColors() {
		return colors;
	}
	public void setColors(ArrayList<Integer> colors) {
		this.colors = colors;
	}

	public ArrayList<Double> getWavestaken() {
		return wavestaken;
	}
	public void setWavestaken(ArrayList<Double> wavestaken) {
		this.wavestaken = wavestaken;
	}

	@Override
	public int getType() {
		return TYPE_TABLE;
	}

}