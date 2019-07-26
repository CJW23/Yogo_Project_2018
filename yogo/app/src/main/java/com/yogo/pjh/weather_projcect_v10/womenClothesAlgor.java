package com.yogo.pjh.weather_projcect_v10;


//menclothe나 wmoenclothe객체를 만들고 DB를 통해 가져온 정보(상의,하의)를 Arraylist에 저장후 생성자를 통해 저장 날씨를 통해 날씨별 메소드 실행.(다른 옷 추천할 시 다시 메소드 실행)

import android.util.Log;

import java.util.ArrayList;
import java.util.*;

public class womenClothesAlgor {

    private ClothesItem[] out = new ClothesItem[100];    //아우터목록을 저장할 객체
    private ClothesItem[] uup = new ClothesItem[100];    //상의를 저장할 객체
    private ClothesItem[] outrcm = new ClothesItem[100]; //상의 조합을 저장할 객체
    private ClothesItem[] down = new ClothesItem[100];
    private int outct = 0;
    private int uupct = 0;
    private int downct = 0;
    private int outrcmct = 0;

    public womenClothesAlgor(ArrayList<ClothesItem> ClothesItems, ArrayList<ClothesItem> pantsinfos)       //input 과정
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
                    || ClothesItems.get(i).getName().equals("7부셔츠") || ClothesItems.get(i).getName().equals("반팔셔츠") || ClothesItems.get(i).getName().equals("반팔티셔츠")
                    || ClothesItems.get(i).getName().equals("미니원피스") || ClothesItems.get(i).getName().equals("롱원피스") || ClothesItems.get(i).getName().equals("블라우스")
                    || ClothesItems.get(i).getName().equals("점프슈트")) {
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
            else if(uup[i].getName().equals("긴팔셔츠") || uup[i].getName().equals("긴팔티셔츠") || uup[i].getName().equals("미니원피스") || uup[i].getName().equals("롱원피스") ||
                    uup[i].getName().equals("블라우스") || uup[i].getName().equals("점프슈트"))
                uup[i].setWeight(1);
            else if(uup[i].getName().equals("후드") || uup[i].getName().equals("니트") || uup[i].getName().equals("맨투맨"))
                uup[i].setWeight(2);
        }
        for (int j = 0; j < pantsinfos.size(); j++) {
            down[j] = pantsinfos.get(j);
            downct++;
        }
    }
    private int ranNum(int max){
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
        for (int i = 0; i < outct; i++) {
            if (out[i].getName().equals("가디건")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("후드") && !uup[k].getName().equals("니트")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("코트")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("반팔셔츠") && !uup[k].getName().equals("7부셔츠") && !uup[k].getName().equals("반팔티셔츠") && !uup[k].getName().equals("점프슈트")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("패딩")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("반팔셔츠") && !uup[k].getName().equals("7부셔츠") && !uup[k].getName().equals("점프슈트")
                            && !uup[k].getName().equals("블라우스") && !uup[k].getName().equals("롱원피스") && !uup[k].getName().equals("미니원피스") && !uup[k].getName().equals("반팔셔츠")
                            && !uup[k].getName().equals("반팔티셔츠")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("가죽자켓")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("반팔셔츠") && !uup[k].getName().equals("반팔티셔츠")
                            && !uup[k].getName().equals("7부셔츠") && !uup[k].getName().equals("후드") && !uup[k].getName().equals("점프슈트")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("블레이저")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("후드") && !uup[k].getName().equals("점프슈트") && !uup[k].getName().equals("롱원피스") && !uup[k].getName().equals("미니원피스")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("데님자켓") || out[i].getName().equals("항공점퍼")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("야구점퍼")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("블라우스") && !uup[k].getName().equals("점프슈트")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("집업")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("블라우스") && !uup[k].getName().equals("점프슈트") && !uup[k].getName().equals("롱원피스")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            } else if (out[i].getName().equals("야상")) {
                outrcm[outrcmct] = new ClothesItem(out[i]);
                for (int k = 0; k < uupct; k++) {
                    if (!uup[k].getName().equals("조끼") && !uup[k].getName().equals("블라우스") && !uup[k].getName().equals("점프슈트") && !uup[k].getName().equals("롱원피스")) {
                        outrcm[outrcmct].inputnext(uup[k]);
                    }
                }
                outrcmct++;
            }
        }
    }

    public ArrayList<ArrayList<ClothesItem>> hotsummer() {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < uupct; i++) {
            if (uup[i].getName().equals("반팔티셔츠") || uup[i].getName().equals("반팔셔츠") || uup[i].getName().equals("7부셔츠") || uup[i].getName().equals("블라우스")) {
                for (int j = 0; j < downct; j++) {
                    onerecommand = new ArrayList<>();
                    onerecommand.add(uup[i]);       //옷과
                    onerecommand.add(down[j]);      //바지를 arraylist에 저장
                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장
                    //리스트를 비움
                }
            }
        }
        //랜덤함수를 이용하여 하나를 리턴
        return recommand;        //임시
    }

    public ArrayList<ArrayList<ClothesItem>> summer() {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < outrcmct; i++) {
            if (outrcm[i].getName().equals("가디건")) {
                for (int j = 0; j < 30; j++) {
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            int k = 0;
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                Log.d("드래곤볼 ",Integer.toString(outrcm[i].getNext()[j].getWeight()));
                                Log.d("찾았다 : ", outrcm[i].getNext()[j].getNext()[k].getName());
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
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
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
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
        for (int i = 0; i < uupct; i++) {
            if (uup[i].getWeight() == 1) {
                if (uup[i].getName().equals("긴팔셔츠") || uup[i].getName().equals("블라우스")) {
                    for (int z = 0; z < downct; z++) {
                        if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                            onerecommand = new ArrayList<>();
                            onerecommand.add(uup[i]);
                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                        }
                    }
                } else if (uup[i].getName().equals("롱원피스") || uup[i].getName().equals("미니원피스") || uup[i].getName().equals("점프슈트")) {
                    onerecommand = new ArrayList<>();
                    onerecommand.add(uup[i]);
                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                }
            }
        }

        return recommand;
    }

    public ArrayList<ArrayList<ClothesItem>> coolweather() //17~22도
    {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < uupct; i++) {
            if (uup[i].getName().equals("후드")) {
                for (int z = 0; z < downct; z++) {
                    onerecommand = new ArrayList<>();
                    onerecommand.add(uup[i]);       //옷과
                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                }
            } else if (uup[i].getName().equals("맨투맨")) {
                for (int z = 0; z < downct; z++) {
                    onerecommand = new ArrayList<>();
                    onerecommand.add(uup[i]);       //옷과
                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                }
            }
        }
        for (int i = 0; i < outrcmct; i++) {
            if (outrcm[i].getName().equals("가디건")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                onerecommand = new ArrayList<>();
                                onerecommand.add(outrcm[i]);       //옷과
                                onerecommand.add(outrcm[i].getNext()[j]);
                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                    for (int z = 0; z < downct; z++) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                    }
                                }
                                k++;
                            }
                        }
                    }
                }
            } else if (outrcm[i].getName().equals("데님자켓") || outrcm[i].getName().equals("항공점퍼")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                    for (int z = 0; z < downct; z++) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 1 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 4) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("치마레깅스")) {
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
            } else if (outrcm[i].getName().equals("집업")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (!outrcm[i].getNext()[j].getName().equals("후드")) {
                            if (outrcm[i].getNext()[j].getName().equals("긴팔셔츠")) {

                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                    }
                                }
                                while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                        for (int z = 0; z < downct; z++) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                        }
                                    }
                                    k++;
                                }
                            } else {
                                if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 1 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 4) {
                                    if (!outrcm[i].getNext()[j].getName().equals("롱원피스") && !outrcm[i].getNext()[j].getName().equals("미니원피스")
                                            && outrcm[i].getNext()[j].getName().equals("점프슈트")) {
                                        for (int z = 0; z < downct; z++) {
                                            if (down[z].getName().equals("반바지")) {
                                                onerecommand = new ArrayList<>();
                                                onerecommand.add(outrcm[i]);       //옷과
                                                onerecommand.add(outrcm[i].getNext()[j]);
                                                onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                                recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장


                                            }
                                        }
                                    } else {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
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
                                    if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                    }
                                }
                                while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                    if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                        for (int z = 0; z < downct; z++) {
                                            if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
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
                                        if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
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
        for (int i = 0; i < outrcmct; i++) {
            if (outrcm[i].getName().equals("데님자켓") || outrcm[i].getName().equals("항공점퍼")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {

                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                    for (int z = 0; z < downct; z++) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                if (!outrcm[i].getNext()[j].getName().equals("롱원피스") && !outrcm[i].getNext()[j].getName().equals("미니원피스") && !outrcm[i].getNext()[j].getName().equals("점프슈트")) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!(down[z].getName().equals("치마레깅스") && outrcm[i].getNext()[j].getName().equals("블라우스")) && !(down[z].getName().equals("레깅스")) && outrcm[i].getNext()[j].getName().equals("블라우스")) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                        }
                                    }
                                } else {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                        }

                    }
                }
            } else if (outrcm[i].getName().equals("집업")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {

                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                    for (int z = 0; z < downct; z++) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                if (!outrcm[i].getNext()[j].getName().equals("롱원피스") && !outrcm[i].getNext()[j].getName().equals("미니원피스") && !outrcm[i].getNext()[j].getName().equals("점프슈트")) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!(down[z].getName().equals("치마레깅스") && outrcm[i].getNext()[j].getName().equals("블라우스")) && !(down[z].getName().equals("레깅스")) && outrcm[i].getNext()[j].getName().equals("블라우스")) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);    //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                        }
                                    }
                                } else {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                        }

                    }
                }
            } else if (outrcm[i].getName().equals("야구점퍼")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {

                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 2) {
                                    for (int z = 0; z < downct; z++) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                if (!outrcm[i].getNext()[j].getName().equals("롱원피스") && !outrcm[i].getNext()[j].getName().equals("미니원피스") && !outrcm[i].getNext()[j].getName().equals("점프슈트")) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!(down[z].getName().equals("치마레깅스") && outrcm[i].getNext()[j].getName().equals("블라우스")) && !(down[z].getName().equals("레깅스")) && outrcm[i].getNext()[j].getName().equals("블라우스")) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                        }
                                    }
                                } else {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                        }

                    }
                }
            } else if (outrcm[i].getName().equals("블레이저")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {

                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() == 1) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                            {
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
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("반바지") && !down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
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
            } else if (outrcm[i].getName().equals("가죽자켓")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 0 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 3) {
                                    for (int z = 0; z < downct; z++) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장


                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 2 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 5) {
                                if (!outrcm[i].getNext()[j].getName().equals("롱원피스") && !outrcm[i].getNext()[j].getName().equals("미니원피스") && !outrcm[i].getNext()[j].getName().equals("점프슈트")) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!(down[z].getName().equals("치마레깅스") && outrcm[i].getNext()[j].getName().equals("블라우스")) && !(down[z].getName().equals("레깅스")) && outrcm[i].getNext()[j].getName().equals("블라우스")) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                        }
                                    }
                                } else {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

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
            if (outrcm[i].getName().equals("야구점퍼")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {

                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("린넨바지")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }

                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("린넨바지")) {
                                            onerecommand = new ArrayList<>();
                                            onerecommand.add(outrcm[i]);       //옷과
                                            onerecommand.add(outrcm[i].getNext()[j]);
                                            onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                            onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                            recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                        }
                                    }
                                    k++;
                                }
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 4 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 6) {
                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("린넨바지")) {
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
            } else if (outrcm[i].getName().equals("가죽자켓")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        onerecommand = new ArrayList<>();
                                        onerecommand.add(outrcm[i]);       //옷과
                                        onerecommand.add(outrcm[i].getNext()[j]);
                                        onerecommand.add(outrcm[i].getNext()[j].getNext()[k]);
                                        onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                        recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장


                                    }
                                }
                                k++;
                            }
                        } else {
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 4 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 6) {

                                for (int z = 0; z < downct; z++) {
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
            }else if (outrcm[i].getName().equals("코트")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
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
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 4 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 6) {

                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스")) {
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
        return recommand;
    }
    public ArrayList<ArrayList<ClothesItem>> freeze() {
        ArrayList<ArrayList<ClothesItem>> recommand = new ArrayList<ArrayList<ClothesItem>>();
        ArrayList<ClothesItem> onerecommand;
        for (int i = 0; i < outrcmct; i++) {
            if (outrcm[i].getName().equals("야상")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("린넨바지")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("린넨바지") && !down[z].getName().equals("반바지")) {
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
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 5 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 8) {

                                for (int z = 0; z < downct; z++) {
                                    if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("반바지") && !down[z].getName().equals("린넨바지")) {
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
            else if (outrcm[i].getName().equals("패딩")) {
                for (int j = 0; j < 30; j++) {
                    int k = 0;
                    if (outrcm[i].getNext()[j] != null) {
                        if (outrcm[i].getNext()[j].equals("긴팔셔츠")) {
                            for (int z = 0; z < downct; z++) {
                                if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("린넨바지") && !down[z].getName().equals("반바지")) {
                                    onerecommand = new ArrayList<>();
                                    onerecommand.add(outrcm[i]);       //옷과
                                    onerecommand.add(outrcm[i].getNext()[j]);
                                    onerecommand.add(down[z]);      //바지를 arraylist에 저장
                                    recommand.add(onerecommand);    //list를 추천 목록 리스트에 저장

                                }
                            }
                            while (outrcm[i].getNext()[j].getNext()[k] != null && outrcm[i].getNext()[j] != null) {
                                if (outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() > 1 && outrcm[i].getNext()[j].getWeight() + outrcm[i].getNext()[j].getNext()[k].getWeight() < 4) {
                                    for (int z = 0; z < downct; z++) {
                                        if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("린넨바지") && !down[z].getName().equals("반바지")) {
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
                            if (outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() > 5 && outrcm[i].getWeight() + outrcm[i].getNext()[j].getWeight() < 8) {

                                Log.d("freeze : ", Integer.toString(downct));
                                for (int z = 0; z < downct; z++) {
                                    Log.d("freeze2 : ", Integer.toString(i));

                                    if (!down[z].getName().equals("치마레깅스") && !down[z].getName().equals("레깅스") && !down[z].getName().equals("반바지") && !down[z].getName().equals("린넨바지")) {
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
        return recommand;
    }
}
