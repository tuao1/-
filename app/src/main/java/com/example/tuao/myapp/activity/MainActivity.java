package com.example.tuao.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tuao.myapp.R;
import com.example.tuao.myapp.fragment.AuctionFragment;
import com.example.tuao.myapp.fragment.HomeFragment;
import com.example.tuao.myapp.fragment.ShopCar;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static Activity intance_MainActivity;
    private RadioGroup radioGroup;
    private RadioButton home,auction,shopcar;
    private final static int FRAGMENT_NUMBER_HOME=1,FRAGMENT_NUMBER_AUCTION=2,FRAGMENT_NUMBER_SHOPCAR=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup=findViewById(R.id.radio_group);
        intance_MainActivity=this;

        //初始化RadioButton并完成点击事件
        initButton();
        setOnclick();
        replaceFragment(1);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 实现按钮的监听事件
     */
    private void setOnclick() {
        home.setOnClickListener(this);
        auction.setOnClickListener(this);
        shopcar.setOnClickListener(this);
    }

    /**
     * 实例化按钮
     */
    private void initButton() {
        home=findViewById(R.id.home_page);
        auction=findViewById(R.id.auction);
        shopcar=findViewById(R.id.shop_car);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {//登录注册
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(MainActivity.this,MyAllActivity.class);
            intent.putExtra("fragment_number",2);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(MainActivity.this,MyAllActivity.class);
            intent.putExtra("fragment_number",3);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this,SpanishActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_page:     replaceFragment(FRAGMENT_NUMBER_HOME);      break;
            case R.id.auction:       replaceFragment(FRAGMENT_NUMBER_AUCTION);       break;
            case R.id.shop_car:      replaceFragment(FRAGMENT_NUMBER_SHOPCAR);        break;
        }
    }

    private void replaceFragment(int number) {

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();

            switch (number) {
                case 1:
                    transaction.replace(R.id.f1_main_contain, new HomeFragment(this));
                    break;
                case 2:
                    transaction.replace(R.id.f1_main_contain, new AuctionFragment(this));
                    break;
                case 3:
                    transaction.replace(R.id.f1_main_contain, new ShopCar(this));
                    break;
                default:
                    break;
            }

        transaction.commit();

    }
}
