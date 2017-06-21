package com.unicellular.simpletask.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.unicellular.simpletask.R;
import com.unicellular.simpletask.adapter.ContentFragmentAdapter;
import com.unicellular.simpletask.app.SimpleTaskApplication;
import com.unicellular.simpletask.transformer.VerticalStackTransformer;
import com.unicellular.simpletask.widget.OrientedViewPager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by szc on 2017/3/20.
 *
 */

public class TodayFragment extends Fragment{
    public static final int TODAY_FRAGMENT =0x00;
    public static final String UPDATE_VIEW="UpdateView";
    public static TodayFragment INSTANCE;

    private OrientedViewPager mOrientedViewPager;
    private ContentFragmentAdapter mContentFragmentAdapter;
    private RelativeLayout rlNoTask;
    private List<Fragment> fragments;
    private View view;

    private UpdateViewBroadCast broadCast;
    private int select=0;


    public static TodayFragment getInstance(){
        if (INSTANCE==null){
            INSTANCE=new TodayFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_today,container,false);
        broadCast=new UpdateViewBroadCast();
        getActivity().registerReceiver(broadCast,new IntentFilter(UPDATE_VIEW));
        initView();
        return view;
    }
    void refreshData(){
        SimpleTaskApplication.allTask.clear();
        SimpleTaskApplication.allTask=SimpleTaskApplication.taskDB.readDataByTime(new Date());


    }
    void initView(){
        fragments=new ArrayList<>();
        refreshData();
        rlNoTask= (RelativeLayout) view.findViewById(R.id.rl_no_task);
        mOrientedViewPager= (OrientedViewPager) view.findViewById(R.id.card_view_pager);

        if (SimpleTaskApplication.allTask.size()<=0){
            mOrientedViewPager.setVisibility(View.GONE);
            rlNoTask.setVisibility(View.VISIBLE);
        }else {
            rlNoTask.setVisibility(View.GONE);
            mOrientedViewPager.setVisibility(View.VISIBLE);
        }


        for (int i=0;i<SimpleTaskApplication.allTask.size();i++){
            System.out.println(i);
            CardFragment cardFragment=CardFragment.newInstance(SimpleTaskApplication.allTask.get(i));
            fragments.add(cardFragment);
        }
        mContentFragmentAdapter=new ContentFragmentAdapter(getActivity().getSupportFragmentManager(),fragments);
        mOrientedViewPager.setOrientation(OrientedViewPager.Orientation.VERTICAL);
        mOrientedViewPager.setOffscreenPageLimit(4);
        mOrientedViewPager.setPageTransformer(true,new VerticalStackTransformer(getActivity().getApplicationContext()));
        mOrientedViewPager.setAdapter(mContentFragmentAdapter);
        if (fragments.size()!=0){
            mOrientedViewPager.setCurrentItem(select);
        }
        mOrientedViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadCast!=null){
            getActivity().unregisterReceiver(broadCast);
        }
    }

    class UpdateViewBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case UPDATE_VIEW:
                    initView();
                    break;
            }
        }
    }

}
