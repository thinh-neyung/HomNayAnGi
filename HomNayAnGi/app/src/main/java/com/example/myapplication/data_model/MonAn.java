package com.example.myapplication.data_model;

import java.io.Serializable;
import java.util.ArrayList;

public class MonAn implements Serializable {
    private String ten,thoigian,dokho;
    private String hinhanh;
    private String theloai;
    public ArrayList<String> nguyen_lieu;
    public ArrayList<cac_buoc> list_cac_buoc;

    public MonAn() {
    }

    public MonAn(String ten, String dokho, String hinhanh, String theloai, ArrayList<String> nguyen_lieu, ArrayList<cac_buoc> list_cac_buoc) {
        this.ten = ten;
        this.dokho = dokho;
        this.hinhanh = hinhanh;
        this.theloai = theloai;
        this.nguyen_lieu = nguyen_lieu;
        this.list_cac_buoc = list_cac_buoc;
    }

    public ArrayList<String> getNguyen_lieu() {
        return nguyen_lieu;
    }

    public void setNguyen_lieu(ArrayList<String> nguyen_lieu) {
        this.nguyen_lieu = nguyen_lieu;
    }

    public ArrayList<cac_buoc> getList_cac_buoc() {
        return list_cac_buoc;
    }

    public void setList_cac_buoc(ArrayList<cac_buoc> list_cac_buoc) {
        this.list_cac_buoc = list_cac_buoc;
    }

    public String getTheloai() {
        return theloai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian() {
        int tongtg=0;
        for (int i=0;i<list_cac_buoc.size();i++)
        {
            tongtg+=Integer.parseInt(list_cac_buoc.get(i).getTime());
        }
        this.thoigian = String.valueOf(tongtg)+"m";
    }

    public String getDokho() {
        return dokho;
    }

    public void setDokho(String dokho) {
        this.dokho = dokho;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }
}
