package com.yogo.pjh.weather_projcect_v10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private AlertDialog dialog;

    private String userID;
    private String userPassword;
    private String userName;
    private int userAge;
    private String userGender;
    private String userEmail;
    private Boolean loginChecked;
    Fragment fragment1;
    Fragment fragment2;
    TextView navid;
    TextView navemail;


    SharedPreferences settings;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5, fab6;
    private TextView fabText1,fabText2,fabText3,fabText4,fabText5,fabText6;

    int frameid = -1;

    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameid = R.id.container;

        //플로팅버튼
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab6 = (FloatingActionButton) findViewById(R.id.fab6);

        fabText1 = (TextView) findViewById(R.id.fabtext1);
        fabText2 = (TextView) findViewById(R.id.fabtext2);
        fabText3 = (TextView) findViewById(R.id.fabtext3);
        fabText6 = (TextView) findViewById(R.id.fabtext6);

        //플로팅 버튼 색깔지정
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled},
                new int[] { android.R.attr.state_pressed}
        };
        int[] colors = new int[] {
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.white)
        };
        ColorStateList fabColorList = new ColorStateList(states, colors);
        findViewById(R.id.fab).setBackgroundTintList(fabColorList);
        findViewById(R.id.fab1).setBackgroundTintList(fabColorList);
        findViewById(R.id.fab2).setBackgroundTintList(fabColorList);
        findViewById(R.id.fab3).setBackgroundTintList(fabColorList);
        findViewById(R.id.fab6).setBackgroundTintList(fabColorList);

        //툴바만들기
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //네비게이션
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //액션바 내비게이션 아이콘생성
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //데이터 가져오기
        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        loginChecked = settings.getBoolean("loginChecked", false);
        if (loginChecked) {
            userID = settings.getString("userID", "");
            userPassword = settings.getString("userPassword", "");
            userName = settings.getString("userName", "");
            userAge = settings.getInt("userAge", -1);
            userGender = settings.getString("userGender", "");
            userEmail = settings.getString("userEmail", "");
            //new GetData().execute();
        }


        //플로팅버튼 클릭
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID != null) {
                    anim();
                    Intent intent = new Intent(MainActivity.this, ShowFriendList.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID != null) {
                    anim();
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID != null) {
                    anim();
                    Intent intent = new Intent(MainActivity.this, MyClosetActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                }
            }
        });


        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID != null) {
                    anim();
                    Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        //네이게이션 셀렉트
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header_view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navid = (TextView)nav_header_view.findViewById(R.id.nav_user_name);
        navemail = (TextView)nav_header_view.findViewById(R.id.nav_user_email);

        if(userID != null)
        {
            navid.setText(userName);
            navemail.setText(userEmail);
        }
        else
        {
            navid.setText("로그인");
            navemail.setText("");
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        fragment2 = new TodayWeatherFragment();
        fragment1 = new TodayRecommendCodi();
        //fragment3=new BlankFragment();

        getHashKey();

       /* //사용설명서 액티비티
        Intent intent = new Intent(MainActivity.this, GuideActivity.class);
        intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);*/

    }

    private void getHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.yogo.pjh.weather_projcect_v10", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("keyyyyyhash","key_hash="+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    //플로팅버튼을위한메소드
    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab6.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab6.setClickable(false);
            fabText1.setAnimation(fab_close);
            fabText2.setAnimation(fab_close);
            fabText3.setAnimation(fab_close);
            fabText6.setAnimation(fab_close);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab6.startAnimation(fab_open);
            fabText1.setAnimation(fab_open);
            fabText2.setAnimation(fab_open);
            fabText3.setAnimation(fab_open);
            fabText6.setAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab6.setClickable(true);
            isFabOpen = true;
        }
    }

    //네비게이션을 위한 메소드
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (id == R.id.nav_0) {
            // 날씨/코디
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.nav_1) {
            // 옷장
            if (userID != null) {
                Intent intent = new Intent(MainActivity.this, MyClosetActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }


        } else if (id == R.id.nav_2) {

            if(userID != null) {
                Intent intent=new Intent(MainActivity.this, RegisterClothesActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPassword", userPassword);
                intent.putExtra("userName", userName);
                intent.putExtra("userAge", userAge);
                intent.putExtra("userGender", userGender);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

        } else if (id == R.id.nav_3) {
            //내 정보
            if (userID != null) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                MainActivity.this.startActivity(intent);
            }
        }else if (id == R.id.nav_4) {
            //내 정보
            if (userID != null) {
                Intent intent = new Intent(MainActivity.this, FindFriendActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                MainActivity.this.startActivity(intent);
            }
        }

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //로그인여부에따라 실행되는 액티비티가다름
        if (loginChecked) {
            //noinspection SimplifiableIfStatement
            if (id == R.id.user_info) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class); //UserInfoActivity로 넘어가기 전에 Intent에 넣음
                intent.putExtra("userID", userID);
                intent.putExtra("userPassword", userPassword);
                intent.putExtra("userName", userName);
                intent.putExtra("userAge", userAge);
                intent.putExtra("userGender", userGender);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                MainActivity.this.startActivity(intent);
                return true;
            }
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            MainActivity.this.startActivity(intent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle bundle = new Bundle();
            bundle.putString("userGender", userGender);
            bundle.putString("userID", userID);
            bundle.putBoolean("loginChecked", loginChecked);
            bundle.putString("userPassword", userPassword);
            bundle.putInt("userAge", userAge);
            bundle.putString("userName", userName);
            bundle.putString("userEmail", userEmail);

            if (position == 0) {
                fragment1.setArguments(bundle);
                return fragment1;
            } else {

                fragment2.setArguments(bundle);
                return fragment2;

            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    /*ublic void setCodiUiTemp(int getTemp, String getWeatherState, int time){
        TodayRecommendCodi tf = (TodayRecommendCodi)getSupportFragmentManager().findFragmentById(R.id.container);
        tf.changeUiTemp(getTemp, getWeatherState, time);
    }*/



}





