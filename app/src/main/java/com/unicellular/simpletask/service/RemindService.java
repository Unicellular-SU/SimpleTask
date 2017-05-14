package com.unicellular.simpletask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.unicellular.simpletask.R;
import com.unicellular.simpletask.app.SimpleTaskApplication;
import com.unicellular.simpletask.bean.Task;
import com.unicellular.simpletask.db.TaskDB;
import com.unicellular.simpletask.fragment.TodayFragment;
import com.unicellular.simpletask.utils.NotificationUtils;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by szc on 2017/3/22.
 *
 */



public class RemindService extends Service{
    private Timer timer;
    private Date date;
    private List<Task> tasks;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer=new Timer();
        timer.schedule(new RemindTimerTask(),0,1000);
        return super.onStartCommand(intent, flags, startId);
    }

    private class RemindTimerTask extends TimerTask{

        @Override
        public void run() {
            date=new Date();
            tasks= SimpleTaskApplication.taskDB.readDataByTimeAndNoOff(new Date());
            if(tasks.size()==0){
                return;
            }
            Task task=tasks.get(0);
            if (date.getTime()>=task.getTime().getTime()){
                SimpleTaskApplication.taskDB.updateValue(task.getId(), TaskDB.KEY_STATUS,1);
                sendBroadcast(new Intent(TodayFragment.UPDATE_VIEW));
                NotificationUtils.reviewNotification(getApplicationContext()
                        , R.drawable.ic_notifications_black_24dp
                        ,"SimpleTask"
                        ,task.getName()
                        ,task.getContext());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
