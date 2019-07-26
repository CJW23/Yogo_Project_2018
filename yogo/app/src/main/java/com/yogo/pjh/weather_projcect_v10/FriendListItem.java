package com.yogo.pjh.weather_projcect_v10;

public class FriendListItem {
    private String textStr ;
    private String btn1;
    private String btn2;

    public String getBtn1() {
        return btn1;
    }

    public String getBtn2() {
        return btn2;
    }

    public void setText(String text) {
        textStr = text ;

    }

    public FriendListItem(String textStr, String btn1, String btn2) {
        this.textStr = textStr;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public String getText() {
        return this.textStr ;
    }
}
