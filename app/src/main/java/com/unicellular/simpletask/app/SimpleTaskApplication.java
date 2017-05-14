package com.unicellular.simpletask.app;

import android.app.Application;
import android.content.Context;

import com.unicellular.simpletask.bean.Task;
import com.unicellular.simpletask.db.TaskDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by szc on 2017/3/20.
 *
 */

public class SimpleTaskApplication extends Application{
    public static Context context;
    public static TaskDB taskDB;

    public static List<Task> allTask;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

        initDatabase();
    }

    void initDatabase(){
        taskDB=new TaskDB(context);
        allTask=new ArrayList<>();
        allTask=SimpleTaskApplication.taskDB.readDataByTime(new Date());
        //taskDB.removeAllData();
    }
}
