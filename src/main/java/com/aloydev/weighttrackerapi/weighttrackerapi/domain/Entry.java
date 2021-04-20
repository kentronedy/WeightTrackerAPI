package com.aloydev.weighttrackerapi.weighttrackerapi.domain;

public class Entry {

    private int id;
    private String username;
    private Long dateInt;
    private String date;
    private Double weight;
    private Double sleep;

    public Entry(int id, String username, Long dateInt, String date, Double weight, Double sleep) {
        this.id = id;
        this.username = username;
        this.dateInt = dateInt;
        this.date = date;
        this.weight = weight;
        this.sleep = sleep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDateInt() {
        return dateInt;
    }

    public void setDateInt(Long dateInt) {
        this.dateInt = dateInt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getSleep() {
        return sleep;
    }

    public void setSleep(Double sleep) {
        this.sleep = sleep;
    }
}
