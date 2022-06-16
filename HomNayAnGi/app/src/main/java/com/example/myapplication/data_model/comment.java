package com.example.myapplication.data_model;

public class comment {
    String username;
    String comment;

    public  comment(){}
    public comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
