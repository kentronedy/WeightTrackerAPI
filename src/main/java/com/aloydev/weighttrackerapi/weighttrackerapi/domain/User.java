package com.aloydev.weighttrackerapi.weighttrackerapi.domain;

public class User {

    private String username;
    private String password;
    private double goal;
    private int permission;

    public User(String username, String password, double goal, int permission) {
        this.username = username;
        this.password = password;
        this.goal = goal;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}

