package com.yogo.pjh.weather_projcect_v10;

import android.net.Uri;

public class Galleryitem {





    String title;
    Uri img;
    String uid;
    String date;
    String des;
    int postid;
    public Galleryitem(String title, Uri img, String date, String uid, String des, int postid) {
        this.title = title;
        this.img = img;
        this.date = date;
        this.uid = uid;
        this.des = des;
        this.postid = postid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public Uri getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public String getUid() {
        return uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
