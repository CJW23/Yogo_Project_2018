package com.yogo.pjh.weather_projcect_v10;

public class Boarditem {
    String uid;
    String des;
    String date;


    public Boarditem(String uid, String des, String date) {
        this.uid = uid;
        this.des = des;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
