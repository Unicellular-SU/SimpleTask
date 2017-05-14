package com.unicellular.simpletask.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

/**
 * Created by szc on 2017/3/22.
 */

public class NotificationUtils {
    public static final int NOTIFICATION_ID=0x123;
    public static void reviewNotification(Context context,int id,String ticker,String title, String text){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(id)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();
        notificationManager.notify(NOTIFICATION_ID,notification);
    }


}
