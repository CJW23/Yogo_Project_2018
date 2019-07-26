package com.yogo.pjh.weather_projcect_v10;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.os.AsyncTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.bumptech.glide.Glide;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class TodayWeatherFragment extends Fragment {

    public static int TO_GRID = 0;
    public static int TO_GPS = 1;
    private Button btnShowLocation;
    private TextView wttxet;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    public static int temp;
    public static String weatherstate;
    public static boolean checkedGPS = false;


    private int time = -100;
    // GPSTracker class
    private GpsInfo gps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_today_weather, container, false);

        return rootView;
    }

    String[] wtstate;
    TextView textView;
    Document doc = null;
    LinearLayout layout = null;
    String location;
    String nowweather;
    ArrayList<String> timeweather;
    TextView temptext;
    TextView[] wttext;
    ImageView[] wtimg;
    ImageView nowwtimg;
    ImageView weatherscreen;
    ImageView hangerImage;
    SharedPreferences settings;
    private Boolean loginChecked;
    private String userID;
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        textView = (TextView) getView().findViewById(R.id.textview2);
        timeweather = new ArrayList<>();
        wttext = new TextView[15];
        wtimg = new ImageView[15];
        nowwtimg = (ImageView)getView().findViewById(R.id.nowwtimg1);
        wttext[0] = (TextView) getView().findViewById(R.id.wttext1);
        wttext[1] = (TextView) getView().findViewById(R.id.wttext2);
        wttext[2] = (TextView) getView().findViewById(R.id.wttext3);
        wttext[3] = (TextView) getView().findViewById(R.id.wttext4);
        wttext[4] = (TextView) getView().findViewById(R.id.wttext5);
        wttext[5] = (TextView) getView().findViewById(R.id.wttext6);
        wttext[6] = (TextView) getView().findViewById(R.id.wttext7);
        wttext[7] = (TextView) getView().findViewById(R.id.wttext8);
        wttext[8] = (TextView) getView().findViewById(R.id.wttext9);
        wttext[9] = (TextView) getView().findViewById(R.id.wttext10);
        wttext[10] = (TextView) getView().findViewById(R.id.wttext11);
        wttext[11] = (TextView) getView().findViewById(R.id.wttext12);
        wttext[12] = (TextView) getView().findViewById(R.id.wttext13);
        wttext[13] = (TextView) getView().findViewById(R.id.wttext14);
        wttext[14] = (TextView) getView().findViewById(R.id.wttext15);
        wtimg[0] = (ImageView) getView().findViewById(R.id.wtimg1);
        wtimg[1] = (ImageView) getView().findViewById(R.id.wtimg2);
        wtimg[2] = (ImageView) getView().findViewById(R.id.wtimg3);
        wtimg[3] = (ImageView) getView().findViewById(R.id.wtimg4);
        wtimg[4] = (ImageView) getView().findViewById(R.id.wtimg5);
        wtimg[5] = (ImageView) getView().findViewById(R.id.wtimg6);
        wtimg[6] = (ImageView) getView().findViewById(R.id.wtimg7);
        wtimg[7] = (ImageView) getView().findViewById(R.id.wtimg8);
        wtimg[8] = (ImageView) getView().findViewById(R.id.wtimg9);
        wtimg[9] = (ImageView) getView().findViewById(R.id.wtimg10);
        wtimg[10] = (ImageView) getView().findViewById(R.id.wtimg11);
        wtimg[11] = (ImageView) getView().findViewById(R.id.wtimg12);
        wtimg[12] = (ImageView) getView().findViewById(R.id.wtimg13);
        wtimg[13] = (ImageView) getView().findViewById(R.id.wtimg14);
        wtimg[14] = (ImageView) getView().findViewById(R.id.wtimg15);
        weatherscreen = (ImageView) getView().findViewById(R.id.WeatherScreen);
        //weatherscreen.setBackground(drawble);
        //weatherscreen.setClipToOutline(true);
        hangerImage=(ImageView)getView().findViewById(R.id.hangerImage);
        hangerImage.setOnClickListener(new hangerListener());

        settings = getActivity().getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            userID = settings.getString("userID", "");
        }

        GetXMLTask task = new GetXMLTask();

        // 권한 요청을 해야 함
        if (!isPermission) {
            callPermission();
            if(isPermission) {
                gps = new GpsInfo(getActivity());
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    LatXLngY grid = convertGRID_GPS(TO_GRID, latitude, longitude);
                    Log.d("GPS 클릭else","1");

                    task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + grid.x + "&gridy=" + grid.y);
                } else {
                    // GPS 를 사용할수 없으rru므로
                    gps.showSettingsAlert();
                }
            }
        }
        else {
            Log.d("GPS 클릭else","2");
            gps = new GpsInfo(getActivity());
            // GPS 사용유무 가져오기
            if (gps.isGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                LatXLngY grid = convertGRID_GPS(TO_GRID, latitude, longitude);

                task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + grid.x + "&gridy=" + grid.y);

            } else {
                // GPS 를 사용할수 없으rru므로
                gps.showSettingsAlert();
            }

        }

    }

    class hangerListener implements View.OnClickListener {
        public void onClick(View v){
            if (userID != null) {
                Intent intent = new Intent(getActivity(), MyClosetActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);

        } else {

            isPermission = true;
        }

    }


    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return doc;
        }

        protected void onPostExecute(Document doc) {
            super.onPostExecute(doc);//이 부분에서 날씨 이미지를 출력해줌
            String s = "";
            wtstate = new String[15];
            int nowTime = -100;

            //dara 태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            NodeList nodeList = doc.getElementsByTagName("data");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환


            for (int i = 0; i < 15; i++) {////////////////////////////////for문 시작
                //날씨 데이터를 추출
                s = "";
                Node node = nodeList.item(i); //data엘리먼트 노드
                Element fstElmnt = (Element) node;

                NodeList timeList = fstElmnt.getElementsByTagName("hour");          //시간 timeList
                s += timeList.item(0).getChildNodes().item(0).getNodeValue() + "시 ";
                time = Integer.parseInt(timeList.item(0).getChildNodes().item(0).getNodeValue());
                NodeList nameList = fstElmnt.getElementsByTagName("temp");          //이름
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();

                if(i==0)        //현재 날시
                {
                    nowweather = ((Node) nameList.item(0)).getNodeValue();
                    temp = (int)Float.parseFloat(nowweather);
                    nowweather = Integer.toString(temp)+"°C";


                }
                s += ((Node) nameList.item(0)).getNodeValue() + "°C\n\n";


                NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");

                // s += websiteList.item(0).getChildNodes().item(0).getNodeValue() + "\n";


                if(websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("구름 많음") || websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("흐림"))
                {
                    wtimg[i].setImageResource(R.drawable.wcloude);
                    if(i==0) {
                        nowwtimg.setImageResource(R.drawable.wcloude);
                        weatherstate = "manycloude";
                        if(time >= 18 || time<6) {
                            Glide.with(getContext()).load(R.drawable.sunny_n2).into(weatherscreen);
                            nowTime=time;
                        }
                        else {
                            Glide.with(getContext()).load(R.drawable.sunny_m2).into(weatherscreen);
                            nowTime=time;
                        }

                    }
                }
                else if(websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("구름 조금") && (time >= 18 || time<6))
                {
                    wtimg[i].setImageResource(R.drawable.wnightcloude);
                    if(i==0 && time != 18) {
                        nowwtimg.setImageResource(R.drawable.wnightcloude);
                        weatherstate = "nightcloude";
                        Glide.with(getContext()).load(R.drawable.sunny_n2).into(weatherscreen);
                        nowTime=time;
                    }
                    else if(i==0 && time==18) {
                        nowwtimg.setImageResource(R.drawable.wsunnycloude);
                        weatherstate = "sunnycloude";
                        Glide.with(getContext()).load(R.drawable.sunny_m2).into(weatherscreen);
                        nowTime=time;
                    }

                }
                else if(websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("구름 조금") && (time >= 6 && time <18))
                {
                    wtimg[i].setImageResource(R.drawable.wsunnycloude);
                    if(i==0 && time != 6) {
                        nowwtimg.setImageResource(R.drawable.wsunnycloude);
                        weatherstate = "sunnycloude";
                        Glide.with(getContext()).load(R.drawable.sunny_m2).into(weatherscreen);
                        nowTime=time;
                    }
                    else if(i==0 && time==6) {
                        nowwtimg.setImageResource(R.drawable.wnightcloude);
                        Glide.with(getContext()).load(R.drawable.sunny_n2).into(weatherscreen);
                        weatherstate = "nightcloude";
                        nowTime=time;
                    }

                }
                else if(websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("맑음") && (time >= 6 && time<18))
                {
                    wtimg[i].setImageResource(R.drawable.wsunny);
                    if(i==0 && time != 6) {
                        nowwtimg.setImageResource(R.drawable.wsunny);
                        Glide.with(getContext()).load(R.drawable.sunny_m2).into(weatherscreen);
                        weatherstate = "sunny";
                        nowTime=time;
                    }
                    else if(i==0 && time==6) {
                        nowwtimg.setImageResource(R.drawable.night);
                        Glide.with(getContext()).load(R.drawable.sunny_n2).into(weatherscreen);
                        weatherstate = "night";
                        nowTime=time;
                    }

                }
                else if(websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("맑음") && (time >= 18 || time<6))
                {
                    wtimg[i].setImageResource(R.drawable.wnightsunny);
                    if(i==0 && time != 18) {
                        nowwtimg.setImageResource(R.drawable.wnightsunny);
                        Glide.with(getContext()).load(R.drawable.sunny_n2).into(weatherscreen);
                        weatherstate = "night";
                        nowTime=time;
                    }
                    else if(i==0 && time==18) {
                        nowwtimg.setImageResource(R.drawable.wsunny);
                        Glide.with(getContext()).load(R.drawable.sunny_m2).into(weatherscreen);
                        weatherstate = "sunny";
                        nowTime=time;
                    }

                }
                else if(websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("비"))
                {
                    wtimg[i].setImageResource(R.drawable.wrainy);
                    if(i==0) {
                        nowwtimg.setImageResource(R.drawable.wrainy);
                        weatherstate = "rainy";
                        if(time >= 18 || time<6)
                            Glide.with(getContext()).load(R.drawable.sunny_n2).into(weatherscreen);
                        else
                            Glide.with(getContext()).load(R.drawable.sunny_m2).into(weatherscreen);
                        nowTime=time;
                    }
                }
                else if(websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("눈") || websiteList.item(0).getChildNodes().item(0).getNodeValue().equals("눈/비"))
                {
                    wtimg[i].setImageResource(R.drawable.wsnow);
                    if(i==0) {
                        nowwtimg.setImageResource(R.drawable.wsnow);
                        weatherstate = "snow";
                        if(time >= 18 || time<6)
                            Glide.with(getContext()).load(R.drawable.sunny_n2).into(weatherscreen);
                        else
                            Glide.with(getContext()).load(R.drawable.sunny_m2).into(weatherscreen);
                    }
                }

                timeweather.add(s);
            }////////////////////////////for문 종료

            //액티비티함수호출
            // ((MainActivity)getActivity()).setCodiUiTemp(temp, weatherstate, nowTime);


            for(int i=0; i<15; i++) {
                wttext[i].setTextColor(Color.WHITE);
                wttext[i].setText(timeweather.get(i).toString());

            }
            textView.setText(nowweather);
            textView.setTextColor(Color.WHITE);

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("HH");
            String formatDate = sdfNow.format(date);
            Log.d("시간 : ", formatDate);


        }
    }
    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y )
    {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        //
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        //


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }



    class LatXLngY
    {
        public double lat;
        public double lng;

        public double x;
        public double y;

    }


}
