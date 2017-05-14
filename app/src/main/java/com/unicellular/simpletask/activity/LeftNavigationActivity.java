package com.unicellular.simpletask.activity;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.unicellular.simpletask.R;
import com.unicellular.simpletask.fragment.AddFragment;
import com.unicellular.simpletask.fragment.HistoryFragment;
import com.unicellular.simpletask.fragment.TodayFragment;
import com.unicellular.simpletask.service.RemindService;
import com.unicellular.simpletask.utils.AnimatorUtils;
import com.unicellular.simpletask.utils.DialogUtils;


public class LeftNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int nowFragment=0x00;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (nowFragment){
                    case TodayFragment.TODAY_FRAGMENT:
                        replaceFragment(new AddFragment());
                        nowFragment=AddFragment.ADD_FRAGMENT;
                        navigationView.setCheckedItem(R.id.nav_add);
                        floatActionButtonTransaction(1,fab);
                        break;
                    case AddFragment.ADD_FRAGMENT:
                        if (AddFragment.addFragment!=null){
                            if (AddFragment.addFragment.checkValue()){
                                if (AddFragment.addFragment.addToDatabase()){
                                    //AnimatorUtils.floatBeatAnimatorSet(fab);
                                    AddFragment.addFragment.initData();
                                    Snackbar.make(view, "添加成功", Snackbar.LENGTH_LONG).setActionTextColor(Color.RED).setAction("Action", null).show();

                                }else {
                                    Snackbar.make(view, "添加失败!", Snackbar.LENGTH_LONG).setActionTextColor(Color.RED).setAction("Action", null).show();
                                }
                            }
                        }
                        break;
                }
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        replaceFragment(new TodayFragment());
        startService(new Intent(LeftNavigationActivity.this, RemindService.class));
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            replaceFragment(new TodayFragment());
            nowFragment=TodayFragment.TODAY_FRAGMENT;
            fab.setVisibility(View.VISIBLE);
            floatActionButtonTransaction(2,fab);
        } else if (id == R.id.nav_add) {
            replaceFragment(new AddFragment());
            nowFragment=AddFragment.ADD_FRAGMENT;
            fab.setVisibility(View.VISIBLE);
            floatActionButtonTransaction(1,fab);
        } else if (id == R.id.nav_history) {
            replaceFragment(new HistoryFragment());
            nowFragment=HistoryFragment.HISTORY_FRAGMENT;
            fab.setVisibility(View.INVISIBLE);
//        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        }else if (id==R.id.nav_about){
            String message="一个没什么卵用的任务提醒\n\r"
                    +"练练手。。没有任何技术含量";
            DialogUtils.reviewDialog(this,"关于",message);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content,fragment)
                .commit();
    }

    private void floatActionButtonTransaction(int type,View view){
        AnimatorSet animator=null;
        if (type==1){
            animator = AnimatorUtils.getFloatAddAnimatorSet(view);
        }else if(type==2){
            animator = AnimatorUtils.getFloatResetAnimatorSet(view);
        }
        animator.start();
    }




}
