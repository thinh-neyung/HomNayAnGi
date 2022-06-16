package com.example.myapplication.data_model;

public class Rate {
    private String username;
    private float point;

    public Rate(String username, float point) {
        this.username = username;
        this.point = point;
    }
    public Rate(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
}
