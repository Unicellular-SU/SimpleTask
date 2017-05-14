package com.unicellular.simpletask.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unicellular.simpletask.bean.Task;
import com.unicellular.simpletask.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by szc on 2017/3/20.
 *
 */

public class TaskDB {
    public static final String TABLE_NAME="task";

    public static final String KEY_ID="id";
    public static final String KEY_NAME="name";
    public static final String KEY_CONTEXT="context";
    public static final String KEY_TIME="time";
    public static final String KEY_TAG="tag";
    public static final String KEY_STATUS="status";
    public static final String KEY_DELETE="isDelete";

    public static final String CREATE_TABLE ="CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME+
            "(" +
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME+" VARCHAR(40) NOT NULL," +
            KEY_CONTEXT+" VARCHAR(500) NOT NULL DEFAULT 'Today Task'," +
            KEY_TIME+" LONG NOT NULL," +
            KEY_TAG+" INT DEFAULT 0," +
            KEY_STATUS+" INT DEFAULT 0,"+
            KEY_DELETE+" INT DEFAULT 0" +
            ");";


    private DBHelper dbHelper;

    public TaskDB(Context context){
        if (dbHelper==null)
            dbHelper=new DBHelper(context);
        open();

    }
    private void open() {
            try {
                dbHelper.getReadableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                dbHelper.getWritableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public boolean addTask(Task task){
        ContentValues values=new ContentValues();
        values.put("name",task.getName());
        values.put("context",task.getContext());
        values.put("time",task.getTime().getTime());
        values.put("tag",task.getTag());

        if (dbHelper.getWritableDatabase().insert(TABLE_NAME,null,values)>0){
            return true;
        }
        return false;
    }
    private Task readDataFromCursor(Cursor cursor){
        int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
        boolean isDelete= cursor.getInt(cursor.getColumnIndex(KEY_DELETE)) != 0;
        String name =cursor.getString(cursor.getColumnIndex(KEY_NAME));
        String context=cursor.getString(cursor.getColumnIndex(KEY_CONTEXT));
        int tag=cursor.getInt(cursor.getInt(cursor.getColumnIndex(KEY_TAG)));
        Date time=new Date(cursor.getLong(cursor.getColumnIndex(KEY_TIME)));
        if (time.getTime()<=new Date().getTime()){
            updateValue(id,KEY_STATUS,1);
        }else {
            updateValue(id,KEY_STATUS,0);
        }
        int status=cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
        Task task=new Task(id,name,context,time,tag,status,isDelete);
        return task;
    }

    public Cursor readData(String selection,String[] selectionArgs,String orderBy,String limit){
        return dbHelper
                .getReadableDatabase()
                .query(TABLE_NAME,null,selection,selectionArgs,null,null,orderBy,limit);
    }


    public List<Task> readDataByTime(Date date){
        String selection="time>=? AND time<=? AND isDelete=0";
        List<Task> data=new ArrayList<>();
        Cursor cursor=readData(selection,TimeUtils.getTodayTimeWhereArgs(date),"time ASC",null);
        while (cursor.moveToNext()){
            data.add(readDataFromCursor(cursor));
        }
        cursor.close();
        return data;
    }
    public List<Task> readDataByTimeAndNoOff(Date date){
        String selection="time>=? AND time<=? AND status=0 AND isDelete=0";
        List<Task> data=new ArrayList<>();
        Cursor cursor=readData(selection,TimeUtils.getTodayTimeWhereArgs(date),"time ASC","0,1");
        while (cursor.moveToNext()){
            data.add(readDataFromCursor(cursor));
        }
        cursor.close();
        return data;
    }

    public List<Task> readHistoryData(){
        String selection="status=1 AND isDelete=0";
        List<Task> data=new ArrayList<>();
        Cursor cursor=readData(selection,null,"time ASC",null);
        while (cursor.moveToNext()){
            data.add(readDataFromCursor(cursor));
        }
        cursor.close();
        return data;
    }

    public void updateValue(int id,String key,int value){
        ContentValues values = new ContentValues();
        values.put(key,value);
        dbHelper.getWritableDatabase().update(TABLE_NAME,values,"id=?",new String[]{String.valueOf(id)});
    }

    public void removeAllData(){
        dbHelper.getWritableDatabase().delete(TABLE_NAME,null,null);
    }

    public void deleteDataById(int id){
        dbHelper.getWritableDatabase().delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
    }
}
