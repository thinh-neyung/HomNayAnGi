package com.example.myapplication.data_model;

import java.util.ArrayList;

public class TheLoai {
    private String name;
    private int img;
    public ArrayList<MonAn> MonAn_list;

    public TheLoai(String name, int img, ArrayList<MonAn> monAn_list) {
        this.name = name;
        this.img = img;
        MonAn_list = monAn_list;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public ArrayList<MonAn> getMonAn_list() {
        return MonAn_list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setMonAn_list(ArrayList<MonAn> monAn_list) {
        MonAn_list = monAn_list;
    }
}
