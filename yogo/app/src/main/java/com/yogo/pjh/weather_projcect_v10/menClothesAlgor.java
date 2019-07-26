package com.yogo.pjh.weather_projcect_v10;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


public class menClothesAlgor {


    private ClothesItem[] out = new ClothesItem[100];    //아우터목록을 저장할 객체
    private ClothesItem[] uup = new ClothesItem[100];    //상의를 저장할 객체
    private ClothesItem[] outrcm = new ClothesItem[100]; //상의 조합을 저장할 객체
    private ClothesItem[] down = new ClothesItem[100];
    private int outct = 0;
    private int uupct = 0;
    private int downct = 0;
    private int outrcmct = 0;

    public menClothesAlgor(ArrayList<ClothesItem> ClothesItems, ArrayList<ClothesItem> pantsinfos)       //input 과정
    {

        for (int i = 0; i < ClothesItems.size(); i++) {
            if (ClothesItems.get(i).getName().equals("블레이저") || ClothesItems.get(i).getName().equals("가디건") || ClothesItems.get(i).getName().equals("코트")
                    || ClothesItems.get(i).getName().equals("데님자켓") || ClothesItems.get(i).getName().equals("항공점퍼") || ClothesItems.get(i).getName().equals("패딩")
                    || ClothesItems.get(i).getName().equals("야구점퍼") || ClothesItems.get(i).getName().equals("야상") || ClothesItems.get(i).getName().equals("가죽자켓")
                    || ClothesItems.get(i).getName().equals("집업")) {
                out[outct] = new ClothesItem(ClothesItems.get(i));
                outct++;
            } else if (ClothesItems.get(i).getName().equals("후드") || ClothesItems.get(i).getName().equals("조끼") || ClothesItems.get(i).getName().equals("니트")
                    || ClothesItems.get(i).getName().equals("긴팔셔츠") || ClothesItems.get(i).getName().equals("긴팔티셔츠") || ClothesItems.get(i).getName().equals("맨투맨")
                    || ClothesItems.get(i).getName().equals("7부티셔츠") || ClothesItems.get(i).getName().equals("반팔셔츠") || ClothesItems.get(i).getName().equals("반팔티셔츠")) {
                uup[uupct] = new ClothesItem(ClothesItems.get(i));
                uupct++;
            }
        }
        for(int i=0; i<outct; i++)
        {
            if(out[i].getName().equals("가디건"))
                out[i].setWeight(1);
            else if(out[i].getName().equals("블레이저") || out[i].getName().equals("항공점퍼") || out[i].getName().equals("집업") || out[i].getName().equals("데님자켓"))
                out[i].setWeight(2);
            else if(out[i].getName().equals("야구점퍼") || out[i].getName().equals("가죽자켓") || out[i].getName().equals("코트"))
                out[i].setWeight(3);
            else if(out[i].getName().equals("패딩") || out[i].getName().equals("야상"))
                out[i].setWeight(4);
        }
        for(int i=0; i<uupct; i++)
        {
            if(uup[i].getName().equals("조끼") || uup[i].getName().equals("7부티셔츠") || uup[i].getName().equals("반팔티셔츠") || uup[i].getName().equals("반팔셔츠"))
                uup[i].setWeight(0);
            else if(uup[i].getName().equals("긴팔셔츠") || uup[i].getName().equals("긴팔티셔츠"))
                uup[i].setWeight(1);
            else if(uup[i].getName().equals("후드") || uup[i].getName().equals("니트") || uup[i].getName().equals("맨투맨"))
                uup[i].setWeight(2);
        }

        Log.d("zxcvb    ", Integer.toString(pantsinfos.size()));
        for (int j = 0; j < pantsinfos.size(); j++) {
            down[j] = pantsinfos.get(j);
            downct++;
        }

        String b = Integer.toString(downct);
        Log.d("ssss   ",  b);
    }

    private int ranNum(int max) {
        Random ran = new Random();

        return ran.nextInt(max);
    }

    public void submit() {
        for (int i = 0; i < uupct; i++) {
            if (uup[i].getName().equals("긴팔셔츠")) {
                for (int j = 0; j < uupct; j++) {
                    if (uup[j].getName().equals("조끼") || uup[j].getName().equals("맨투맨") || uup[j].getName().equals("니트")) {
                        uup[i].inputnext(uup[j]);
                    }
                }
            }
        }
        Log.d("why", Integer.toString(outct));
        for (int i = 0; i < outct; i++) {

            if (out[i].getName().equals("가디건")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("후드")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("코트") || out[i].getName().equals("패딩")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("반팔셔츠") && !uup[k].getName().equals("7부셔츠")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("가죽자켓")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("반팔셔츠") && !uup[k].getName().equals("반팔티셔츠")
                            && !uup[k].getName().equals("7부셔츠") && !uup[k].getName().equals("후드")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("블레이저")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("후드")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("데님자켓") || out[i].getName().equals("항공점퍼") && out[i].getName().equals("야구점퍼")
                    && out[i].getName().equals("야상") && out[i].getName().equals("집업")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            }
        }
        Log.d("yayayayaao",Integer.toString(outrcmct));
    }

    public boolean Color(ClothesItem up, ClothesItem down) {

        if(down.getColor().equals("red") || down.getColor().equals("orange")) {

            if(up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("blackcheck")){
                return true;
            }
            else{
                return false;
            }
        }
        else if(down.getColor().equals("yellow")){
            if(up.getColor().equals("blue") || up.getColor().equals("indigo") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("blackcheck")){
                return true;
            }
            else
                return false;
        }
        else if(down.getColor().equals("green")){
            if(up.getColor().equals("red") || up.getColor().equals("orange") || up.getColor().equals("yellow") || up.getColor().equals("blue") || up.getColor().equals("indigo") || up.getColor().equals("purple") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("blackcheck")){
                return true;
            }
            else
                return false;
        }
        else if(down.getColor().equals("blue")){
            if(up.getColor().equals("red") || up.getColor().equals("orange") || up.getColor().equals("yellow") || up.getColor().equals("green") || up.getColor().equals("indigo") || up.getColor().equals("purple") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("blackcheck") || up.getColor().equals("beige") || up.getColor().equals("brown")){
                return true;
            }
            else
                return false;
        }
        else if(down.getColor().equals("indigo")){
            if(up.getColor().equals("red") || up.getColor().equals("orange") || up.getColor().equals("yellow") || up.getColor().equals("green") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("gray") || up.getColor().equals("blackcheck") || up.getColor().equals("beige") || up.getColor().equals("brown")){
                return true;
            }
            else
                return false;
        }
        else if(down.getColor().equals("purple")){
            if(up.getColor().equals("yellow") || up.getColor().equals("green") || up.getColor().equals("blue") || up.getColor().equals("indigo") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("blackcheck")){
                return true;
            }
            else
                return false;
        }
        else if(down.getColor().equals("gray")){
            if(up.getColor().equals("gray") || up.getColor().equals("maxblue") || up.getColor().equals("blackcheck")){
                return false;
            }
            else
                return true;
        }
        else if(down.getColor().equals("blackcheck")) {
            if(up.getColor().equals("indigo") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("gray") || up.getColor().equals("beige") || up.getColor().equals("brown")){
                return true;
            }
            else
                return false;
        }
        else if(down.getColor().equals("redcheck") || down.getColor().equals("greencheck")){
            if(up.getColor().equals("indigo") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("gray")){
                return true;
            }
            else
                return false;
        }
        else if(down.getColor().equals("beige")){
            if(up.getColor().equals("maxblue") || up.getColor().equals("highblue") || up.getColor().equals("midblue") || up.getColor().equals("lowblue") || up.getColor().equals("beige")){
                return false;
            }
            else
                return true;
        }
        else if(down.getColor().equals("brown")) {
            if(up.getColor().equals("orange") || up.getColor().equals("yellow") || up.getColor().equals("black") || up.getColor().equals("white") || up.getColor().equals("blackcheck") || up.getColor().equals("redcheck") || up.getColor().equals("greencheck") || up.getColor().equals("beige")){
                return true;
            }
            else
                return false;
        }
        else
            return true;
    }
    public ArrayList<ArrayList<ClothesItem>> hotsummer() {
        Log.d("ya", Integer.toString(downct));
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < uupct; i++) {
            if (uup[i].getName().equals("반팔티셔츠") || uup[i].getName().equals("반팔셔츠") || uup[i].getName().equals("7부티셔츠")) {
                for (int j = 0; j < downct; j++) {
                    if(Color(uup[i], down[j])) {
                        onerecommand = new ArrayList<>();
                        onerecommand.add(uup[i]);       //옷과
                        onerecommand.add(down[j]);      //바지를 arraylist에 저장
                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                        Log.d("shang   ", recommand.get(0).get(0).getName());
                    }
                }
            }
        }
        //랜덤함수를 이용하여 하나를 리턴
        return recommand;
    }

    public ArrayList<ArrayList<ClothesItem>> summer() {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        Log.d("sibal   ", Integer.toString(outct));
        for (int i = 0; i < outrcmct; i++) {
            if (outrcm[i].getName().equals("가디건")) {
                for (int j = 0; j < 30; j++) {
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                }
                            }
                            int k = 0;
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                    for (int z = 0; z < downct; z++) {
                                        if(Color(outrcm[i].getNext()[j],down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                                k++;
                            }
                        }
                        if (outrcm[i].getNext()[j].getWeight() == 0 && outrcm[i].getNext()[j].getName().equals("조끼")) {
                            for (int z = 0; z < downct; z++) {
                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                }
                            }
                        }
                    }
                }
            }
        }
        String a = Integer.toString(recommand.size());
        Log.d("awdsd    ", a);
        return recommand;
    }

    public ArrayList<ArrayList<ClothesItem>> coolweather() //17~22도
    {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < uupct; i++) {
            if (uup[i].getName().equals("후드")) {
                for (int z = 0; z < downct; z++) {
                    if(Color(uup[i],down[z])) {
                        onerecommand = new ArrayList<>();
                        onerecommand.add(uup[i]);       //옷과
                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                    }
                }
            } else if (uup[i].getName().equals("맨투맨")) {
                for (int z = 0; z < downct; z++) {
                    if(Color(uup[i],down[z])) {
                        onerecommand = new ArrayList<>();
                        onerecommand.add(uup[i]);       //옷과
                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                    }

                }
            }
        }
        for (int i = 0; i < outrcmct; i++) {
            if (outrcm[i].getName().equals("가디건")) {
                Log.d("yayayaya","in");
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {

                            for (int z = 0; z < downct; z++) {
                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                    for (int z = 0; z < downct; z++) {
                                        if (Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                                k++;
                            }
                        }
                    }
                }
            } else if (outrcm[i].getName().equals("데님자켓") || outrcm[i].getName().equals("집업") || outrcm[i].getName().equals("항공점퍼")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;

                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                    for (int z = 0; z < downct; z++) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 1 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 4) {
                                for (int z = 0; z < downct; z++) {
                                    if(Color(outrcm[i].getNext()[j], down[z])) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (outrcm[i].getName().equals("블레이저")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (!outrcm[i].getNext()[j].getName().equals("후드")) {
                            if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                                for (int z = 0; z < downct; z++) {
                                    if(Color(outrcm[i].getNext()[j], down[z])) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                    }
                                }
                                while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                        for (int z = 0; z < downct; z++) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                    k++;
                                }
                            } else {
                                if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 1 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return recommand;
    }

    public ArrayList<ArrayList<ClothesItem>> spring() {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        Log.d("yayayaya",Integer.toString(outrcmct));
        for (int i = 0; i < outrcmct; i++) {

            if (outrcm[i].getName().equals("데님자켓") || outrcm[i].getName().equals("집업") || outrcm[i].getName().equals("항공점퍼") || outrcm[i].getName().equals("야구점퍼")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    Log.d("yayayaya","inon");
                    if (outrcm[i].getNext()[j] != null) {

                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            Log.d("yayayaya","inoon");
                            Log.d("yayayayado",Integer.toString(downct));
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("반바지")) {
                                    if (Color(outrcm[i].getNext()[j], down[z])) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                    }
                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getName().equals("야구점퍼")) {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 2) {
                                        for (int z = 0; z < downct; z++) {
                                            if (!down[z].getName().equals("반바지")) {
                                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                                    onerecommand = new ArrayList<>();
                                                    onerecommand.add(outrcm[i]);       //옷과
                                                    onerecommand.add(outrcm[i].getNext()[j]);
                                                    onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 0) {
                                        for (int z = 0; z < downct; z++) {
                                            if (!down[z].getName().equals("반바지")) {
                                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                                    onerecommand = new ArrayList<>();
                                                    onerecommand.add(outrcm[i]);       //옷과
                                                    onerecommand.add(outrcm[i].getNext()[j]);
                                                    onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                                }
                                            }
                                        }
                                    }
                                }

                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("반바지")) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } else if (outrcm[i].getName().equals("블레이저")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (!outrcm[i].getNext()[j].getName().equals("후드")) {
                            if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                                for (int z = 0; z < downct; z++) {
                                    if(Color(outrcm[i].getNext()[j], down[z])) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                    }
                                }
                                while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 0 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                        for (int z = 0; z < downct; z++) {
                                            if (!down[z].getName().equals("반바지")) {
                                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                                    onerecommand = new ArrayList<>();
                                                    onerecommand.add(outrcm[i]);       //옷과
                                                    onerecommand.add(outrcm[i].getNext()[j]);
                                                    onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                                }
                                            }
                                        }
                                    }
                                    k++;
                                }
                            } else {
                                if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("반바지")) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (outrcm[i].getName().equals("가죽자켓")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (!outrcm[i].getNext()[j].getName().equals("후드")) {
                            if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("반바지")) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                                while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 0 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                        for (int z = 0; z < downct; z++) {
                                            if (!down[z].getName().equals("반바지")) {
                                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                                    onerecommand = new ArrayList<>();
                                                    onerecommand.add(outrcm[i]);       //옷과
                                                    onerecommand.add(outrcm[i].getNext()[j]);
                                                    onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                                }
                                            }
                                        }
                                    }
                                    k++;
                                }
                            } else {
                                if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("반바지")) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return recommand;
    }

    public ArrayList<ArrayList<ClothesItem>> winter() {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < outrcmct; i++) {
            int k = 0;
            if (outrcm[i].getName().equals("가죽자켓")) {
                for (int j = 0; j < 30; j++) {
                    k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (!outrcm[i].getNext()[j].getName().equals("후드")) {
                            if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                                while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                        for (int z = 0; z < downct; z++) {
                                            if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                                if(Color(outrcm[i].getNext()[j], down[z])) {
                                                    onerecommand = new ArrayList<>();
                                                    onerecommand.add(outrcm[i]);       //옷과
                                                    onerecommand.add(outrcm[i].getNext()[j]);
                                                    onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                                }
                                            }
                                        }
                                    }
                                    k++;
                                }
                            } else {
                                if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 4 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 6) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (outrcm[i].getName().equals("coat")) {

                for (int j = 0; j < 30; j++) {
                    k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 4 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 6) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (outrcm[i].getName().equals("야구점퍼")) {
                for (int j = 0; j < 30; j++) {
                    k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 4 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 6) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return recommand;
    }

    public ArrayList<ArrayList<ClothesItem>> freeze() {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < outrcmct; i++) {
            int k = 0;
            if (outrcm[i].getName().equals("야상")) {
                for (int j = 0; j < 30; j++) {
                    k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨바지")) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 5 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 8) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (outrcm[i].getName().equals("패딩")) {
                for (int j = 0; j < 30; j++) {
                    k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                            while (outrcm[i].getNext()[j].getNext()[k] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                            if(Color(outrcm[i].getNext()[j], down[z])) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                            }
                                        }
                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 5 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 8) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("반바지") && !down[z].getName().equals("린넨반지")) {
                                        if(Color(outrcm[i].getNext()[j], down[z])) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return recommand;
    }

}


