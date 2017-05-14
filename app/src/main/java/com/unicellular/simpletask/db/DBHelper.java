package com.unicellular.simpletask.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.unicellular.simpletask.app.SimpleTaskApplication;


/**
 * Created by szc on 2017/3/20.
 *
 */

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME="simple_task.db";
    public static final int VERSION=1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Toast.makeText(SimpleTaskApplication.context, "11111111111111", Toast.LENGTH_SHORT).show();
        try {
            sqLiteDatabase.execSQL(TaskDB.CREATE_TABLE);
            Logger.d("Create Database Success");
        }catch (Exception e){
            Logger.e("Create Database Filed \n"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
