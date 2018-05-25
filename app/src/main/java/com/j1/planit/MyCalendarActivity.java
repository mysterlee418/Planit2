package com.j1.planit;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


public class MyCalendarActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    BottomBar bottomBar;
    ViewPager viewPager;
    FragmentPageAdapter pageAdapter;
    TextView mainText;
    static String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);


        drawerLayout = findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.pager);
        mainText = findViewById(R.id.mainText);
        bottomBar = findViewById(R.id.bottomBar);

        pageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        bottomBar.setOnTabSelectListener(onTabSelectListener);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.setItemIconTintList(null);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        String email = getIntent().getStringExtra("email");


        View header = navigationView.getHeaderView(0);
        TextView headerEmail = header.findViewById(R.id.header_email_id);
        TextView IdName = header.findViewById(R.id.header_id_name);

        headerEmail.setText(email);
        String[] id = email.split("@");
        IdName.setText(id[0]);

        Log.i("email", email);
        emailAddress = email;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.content1:
                        break;
                    case R.id.content2:
                        break;
                    case R.id.content3:
                        break;
                    case R.id.content4:
                        break;

                }
                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_search:
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    public void headerImgClicked (View v){

    }

    OnTabSelectListener onTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(int tabId) {

            if (tabId == R.id.tab_mycalendar){
                mainText.setVisibility(View.VISIBLE);
                mainText.setText("Planit");
                viewPager.setCurrentItem(0, true);
            }else if(tabId == R.id.tab_sharedcalendar){
                mainText.setVisibility(View.VISIBLE);
                mainText.setText("현재 공유중인 캘린더");
                viewPager.setCurrentItem(1,true);
            }else if(tabId == R.id.tab_addplan){
                mainText.setVisibility(View.GONE);
                viewPager.setCurrentItem(2,true);
            }else if(tabId == R.id.tab_findfriends){
                mainText.setVisibility(View.GONE);
                viewPager.setCurrentItem(3,true);
            }else if(tabId == R.id.tab_favoriteplan){
                mainText.setVisibility(View.GONE);
                viewPager.setCurrentItem(4,true);
            }

        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            bottomBar.selectTabAtPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
