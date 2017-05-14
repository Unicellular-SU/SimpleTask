package com.unicellular.simpletask.utils;

import android.app.DatePickerDialog;
import java.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by szc on 2017/3/20.
 */

public class TimeUtils {

    public static final int NOW_YEAR=0;
    public static final int NOW_MONTH=1;
    public static final int NOW_DAY=2;
    public static final int NOW_HOUR=3;
    public static final int NOW_MIN=4;
    public static final int NOW_SECONDS=5;


    public static String[] getTodayTimeWhereArgs(Date date){

        String begin= String.valueOf(getDayBeginTimestamp(date));
        String end= String.valueOf(getDayEndTimestamp(date));
        String whereArgs[]={begin,end};

        return whereArgs;
    }

    public static long getDayBeginTimestamp(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        Date date2 = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60
                * 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND)
                * 1000);
        return date2.getTime();
    }
    public static long getDayEndTimestamp(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int hour=23-gc.get(gc.HOUR_OF_DAY);
        int min=59-gc.get(gc.MINUTE);
        int second=59-gc.get(gc.SECOND);
        Date date2 = new Date(date.getTime() + hour * 60 * 60
                * 1000 + min * 60 * 1000 + second
                * 1000);
        return date2.getTime();
    }


    public static boolean isDateAfter(DatePickerDialog tempView) {
        Calendar mCalendar = null;
        Calendar tempCalendar=null;

            mCalendar = Calendar.getInstance();
            tempCalendar = Calendar.getInstance();
            tempCalendar.set(tempView.getDatePicker().getYear(), tempView.getDatePicker().getMonth(),
                    tempView.getDatePicker().getDayOfMonth(), 0, 0, 0);
            if (tempCalendar.before(mCalendar))
                return true;
            else
                return false;

    }

    public static long stringToTime(String str){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date=new Date();
        try {
            date=sdf.parse(str);
        } catch (ParseException e) {
            System.out.println("WA");
            e.printStackTrace();
        }
        return date.getTime();
    }
    public static String dateToString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getNowTime(int type){
        Calendar calender=Calendar.getInstance();
        int year=calender.get(Calendar.YEAR);
        int month=calender.get(Calendar.MONTH);
        int day=calender.get(Calendar.DAY_OF_MONTH);
        int hour=calender.get(Calendar.HOUR_OF_DAY);
        int min=calender.get(Calendar.MINUTE);
        int seconds=calender.get(Calendar.SECOND);
        switch (type){
            case NOW_YEAR:
                return year+"";
            case NOW_MONTH:
                if (month<10){
                    return "0"+(month+1);
                }
                return (month+1)+"";
            case NOW_DAY:
                if (day<10){
                    return "0"+day;
                }
                return day+"";
            case NOW_HOUR:
                if (hour<10){
                    return "0"+hour;
                }
                return hour+"";
            case NOW_MIN:
                if (min<10){
                    return "0"+min;
                }
                return min+"";
            case NOW_SECONDS:
                return seconds+"";
            default:
                return "";
        }
    }
}

