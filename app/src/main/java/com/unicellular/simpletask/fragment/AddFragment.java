package com.unicellular.simpletask.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicellular.simpletask.R;
import com.unicellular.simpletask.app.SimpleTaskApplication;
import com.unicellular.simpletask.bean.Task;
import com.unicellular.simpletask.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by szc on 2017/3/20.
 *
 */

public class AddFragment extends Fragment implements View.OnClickListener{
    public static final int ADD_FRAGMENT =0x01;
    public static AddFragment INSTANCE;
    private MaterialEditText editTaskName;
    private MaterialEditText editTaskContext;
    private Button btnPickTime;
    private Button btnPickDate;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private String strDate="";
    private String strTime ="";
    private Task task;

    private int selectItem=0;

    private CardView cardView;

    public static AddFragment addFragment;


    public static AddFragment getInstance(){
        if (INSTANCE==null){
            INSTANCE=new AddFragment();
        }
        return INSTANCE;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add,container,false);
        task=new Task();
        initView(view);
        addFragment=this;
        return view;
    }

    void initView(View view){
        cardView= (CardView) view.findViewById(R.id.cardView);
        editTaskName= (MaterialEditText) view.findViewById(R.id.edit_task_name);
        editTaskContext= (MaterialEditText) view.findViewById(R.id.edit_task_context);
        btnPickTime = (Button) view.findViewById(R.id.btn_pick_time);
        btnPickDate= (Button) view.findViewById(R.id.btn_pick_date);
        initDatePicker();


        btnPickDate.setOnClickListener(this);
        btnPickTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_pick_time:
                timePickerDialog.show();
                break;
            case R.id.btn_pick_date:
                datePickerDialog.show();
                break;
        }
    }

    public boolean checkValue(){

        long nowTime=TimeUtils.stringToTime(strDate+" "+TimeUtils.getNowTime(TimeUtils.NOW_HOUR)+":"+TimeUtils.getNowTime(TimeUtils.NOW_MIN));
        long checkedTime=TimeUtils.stringToTime(strDate+" "+strTime);

        if (editTaskName.getText().toString().equals("")||editTaskName.getText()==null){
            editTaskName.setError(getResources().getString(R.string.str_error_no_name));
            cardShootAnimator();
            return false;
        }
        if (strDate==null||strDate.equals("")){
            btnPickDate.setText(getResources().getString(R.string.str_today));
        }
        if (strTime==null||strTime.equals("")){
            btnPickTime.setText(getResources().getString(R.string.str_error_no_time));
            btnPickTime.setTextColor(Color.WHITE);
            btnPickTime.setBackgroundColor(getResources().getColor(R.color.card_color_9));
            cardShootAnimator();
            return false;
        }
        if (nowTime>=checkedTime){
            btnPickTime.setText(getResources().getString(R.string.str_error_early_time));
            btnPickTime.setTextColor(Color.WHITE);
            btnPickTime.setBackgroundColor(getResources().getColor(R.color.card_color_9));
            cardShootAnimator();
            return false;
        }

        return true;
    }

    private void initDatePicker(){
        int year= Integer.parseInt(TimeUtils.getNowTime(TimeUtils.NOW_YEAR));
        int month= Integer.parseInt(TimeUtils.getNowTime(TimeUtils.NOW_MONTH))-1;
        int day= Integer.parseInt(TimeUtils.getNowTime(TimeUtils.NOW_DAY));
        int hour= Integer.parseInt(TimeUtils.getNowTime(TimeUtils.NOW_HOUR));
        int min= Integer.parseInt(TimeUtils.getNowTime(TimeUtils.NOW_MIN));

        strDate=TimeUtils.getNowTime(TimeUtils.NOW_YEAR)
                +"-"+TimeUtils.getNowTime(TimeUtils.NOW_MONTH)
                +"-"+TimeUtils.getNowTime(TimeUtils.NOW_DAY);

        datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String temp1=month+1+"";
                String temp2=day+"";
                if (month<10){
                    temp1="0"+(month+1);
                }if(day<10){
                    temp2="0"+day;
                }
                strDate =year+"-"+temp1+"-"+temp2;
                btnPickDate.setText(strDate);
            }
        },year,month,day);

        timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String temp1=hour+"";
                String temp2=min+"";
                if (hour<10){
                    temp1="0"+hour;
                }if(min<10){
                    temp2="0"+min;
                }
                strTime=temp1+":"+temp2;

                long longTime=TimeUtils.stringToTime(strDate+" "+strTime);
                task.setTime(new Date(longTime));

                btnPickTime.setText(strTime);
                btnPickTime.setTextColor(Color.BLACK);
                btnPickTime.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        }, hour, min, true);
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
    }


    public boolean addToDatabase(){
        task.setName(editTaskName.getText().toString().trim());
        task.setContext(editTaskContext.getText().toString().trim());
        if (task.getTime()==null){
            task.setTime(new Date());
        }
        task.setTag(selectItem);
        return SimpleTaskApplication.taskDB.addTask(task);
    }

    public void cardShootAnimator(){
        TranslateAnimation animation = new TranslateAnimation(0, -10, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(100);
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        cardView.startAnimation(animation);
    }

    public void initData(){
        editTaskName.setText("");
        editTaskContext.setText("");
        strTime="";
        btnPickTime.setText(getResources().getString(R.string.str_pick_time));
        btnPickDate.setText(getResources().getString(R.string.str_today));
    }

}
