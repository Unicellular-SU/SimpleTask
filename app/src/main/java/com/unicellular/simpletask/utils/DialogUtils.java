package com.unicellular.simpletask.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.unicellular.simpletask.app.SimpleTaskApplication;
import com.unicellular.simpletask.fragment.HistoryFragment;
import com.unicellular.simpletask.fragment.TodayFragment;

/**
 * Created by szc on 2017/3/23.
 *
 */

public class DialogUtils {
    public static void reviewDialog(final Context context, String title, String message, final int taskId){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SimpleTaskApplication.taskDB.deleteDataById(taskId);
                context.sendBroadcast(new Intent(TodayFragment.UPDATE_VIEW));
                context.sendBroadcast(new Intent(HistoryFragment.UPDATE_VIEW));
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public static void reviewDialog(Context context, String title, String message){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
