package com.yogo.pjh.weather_projcect_v10;

public class BoardStringItem {
    String date;
    String comment;
    String whosent;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWhosent() {
        return whosent;
    }

    public void setWhosent(String whosent) {
        this.whosent = whosent;
    }

    public BoardStringItem(String email) {
        this.email = email;
    }

    public BoardStringItem(String date, String whosent) {
        this.date = date;
        this.whosent = whosent;
    }

    public BoardStringItem(String date, String comment, String whosent) {
        this.date = date;
        this.comment = comment;
        this.whosent = whosent;
    }
}
