package com.example.myapplication.data_model;

import java.io.Serializable;

public class cac_buoc implements Serializable {
    private String noi_dung;
    private String time;

    public cac_buoc()
    {

    }
    public cac_buoc(String noi_dung, String time) {
        this.noi_dung = noi_dung;
        this.time = time;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
