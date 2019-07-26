package com.yogo.pjh.weather_projcect_v10;

import android.graphics.Bitmap;
import android.net.Uri;

public class ClothesItem {

    private Bitmap centerImage;
    private Uri uri_image;
    private String color;
    private String name;
    private int clothes_id;

    private int weight;
    private String def;
    private ClothesItem[] next = new ClothesItem[30];

    int nextcnt=0;

    private int priority = 0;


    public void inputnext(ClothesItem nextclt)
    {
        this.next[nextcnt] = nextclt;
        nextcnt++;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getClothes_id() {
        return clothes_id;
    }

    public void setClothes_id(int clothes_id) {
        this.clothes_id = clothes_id;
    }

    public String getColor(){return color;}

    public void setColor(String color){this.color = color;}

    public String getName() {
        return name;
    }

    public ClothesItem[] getNext() {
        return next;
    }

    public void setNext(ClothesItem[] next) {
        this.next = next;
    }

    public int getNextcnt() {
        return nextcnt;
    }

    public void setNextcnt(int nextcnt) {
        this.nextcnt = nextcnt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public ClothesItem(ClothesItem bit)
    {
        this.centerImage=bit.getCenterImage();
        this.uri_image=bit.getUri_image();
        this.weight=bit.getWeight();
        this.def=bit.getDef();
        this.clothes_id=bit.getClothes_id();
        this.nextcnt=bit.getNextcnt();
        this.name=bit.getName();
        this.next=bit.getNext();
        this.color=bit.getColor();
        this.priority=bit.getPriority();
    }

    public ClothesItem(Uri uri, String name, String def, int clothes_id)     //input함수 과정
    {
        this.name = name;
        this.def = def;
        if(name.equals("블레이저") || name.equals("데님자켓") || name.equals("블레이저") || name.equals("항공점퍼") || name.equals("집업") || name.equals("후드") || name.equals("맨투맨")){
            this.weight = 2;
        }
        else if(name.equals("가디건") || name.equals("긴팔셔츠") || name.equals("긴팔티셔츠") || name.equals("미니원피스") || name.equals("롱원피스") || name.equals("블라우스") || name.equals("점프수트")){
            this.weight = 1;
        }
        else if(name.equals("조끼") || name.equals("7부티셔츠") || name.equals("반팔셔츠") || name.equals("반팔티셔츠")){
            this.weight = 0;
        }
        else if(name.equals("코트") || name.equals("야구점퍼") || name.equals("가죽자켓")){
            this.weight = 3;
        }
        else if(name.equals("패딩") || name.equals("야상")){
            this.weight = 4;
        }
        this.uri_image = uri;
        this.clothes_id=clothes_id;
    }

    public ClothesItem(Uri uri, String name, String def, int clothes_id, String color)     //input함수 과정
    {
        this.name = name;
        this.def = def;
        if(name.equals("블레이저") || name.equals("데님자켓") || name.equals("블레이저") || name.equals("항공점퍼") || name.equals("집업") || name.equals("후드") || name.equals("맨투맨")){
            this.weight = 2;
        }
        else if(name.equals("가디건") || name.equals("긴팔셔츠") || name.equals("긴팔티셔츠") || name.equals("미니원피스") || name.equals("롱원피스") || name.equals("블라우스") || name.equals("점프수트")){
            this.weight = 1;
        }
        else if(name.equals("조끼") || name.equals("7부티셔츠") || name.equals("반팔셔츠") || name.equals("반팔티셔츠")){
            this.weight = 0;
        }
        else if(name.equals("코트") || name.equals("야구점퍼") || name.equals("가죽자켓")){
            this.weight = 3;
        }
        else if(name.equals("패딩") || name.equals("야상")){
            this.weight = 4;
        }
        this.uri_image = uri;
        this.clothes_id=clothes_id;
        this.color=color;
    }

    public Uri getUri_image() {
        return uri_image;
    }

    public void setUri_image(Uri uri_image) {
        this.uri_image = uri_image;
    }

    public ClothesItem(Uri uri_image) {
        this.uri_image = uri_image;
    }

    public ClothesItem(Bitmap centerImage) {
        this.centerImage = centerImage;
    }

    public Bitmap getCenterImage() {
        return centerImage;
    }

    public void setCenterImage(Bitmap centerImage) {
        this.centerImage = centerImage;
    }

}
