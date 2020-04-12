package com.voltiosx.wavescore.models;

import java.util.Date;
import java.util.List;

public class Tournament {
    private Date timestamp;
    private String owner;
    private int points;
    private int rating;
    private String name;
    private String city;
    private String location;
    private String description;
    private Double pricemoney;
    private Date dateini;
    private String time;
    private String status;
    private List<Category> categorys;
    private String form;
    private String ss;

    public Tournament() {

    } // Necesario para Firebase

    public Tournament(Date timestamp, String owner, int points, int rating, String name, String city, String location, String description, Double pricemoney, Date dateini, String time, String status, List<Category> categorys, String form, String ss) {
        this.timestamp = timestamp;
        this.owner = owner;
        this.points = points;
        this.rating = rating;
        this.name = name;
        this.city = city;
        this.location = location;
        this.description = description;
        this.pricemoney = pricemoney;
        this.dateini = dateini;
        this.time = time;
        this.status = status;
        this.categorys = categorys;
        this.form = form;
        this.ss = ss;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPricemoney() {
        return pricemoney;
    }

    public void setPricemoney(Double pricemoney) {
        this.pricemoney = pricemoney;
    }

    public Date getDateini() {
        return dateini;
    }

    public void setDateini(Date dateini) {
        this.dateini = dateini;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Category> categorys) {
        this.categorys = categorys;
    }

    public String getFormUrl() {
        return form;
    }

    public void setFormUrl(String form) {
        this.form = form;
    }

    public String getSS() {
        return ss;
    }

    public void setSS(String ss) {
        this.ss = ss;
    }
}