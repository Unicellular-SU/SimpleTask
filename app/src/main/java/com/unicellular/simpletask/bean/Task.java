package com.unicellular.simpletask.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by szc on 2017/3/20.
 *
 */

public class Task implements Serializable {
    private int id;
    private String name;
    private String context;
    private Date time;
    private int tag;
    private int status;
    private boolean isDelete;

    public Task() {
    }

    public Task(int id,String name, String context, Date time, int tag,int status, boolean isDelete) {
        this.id=id;
        this.name = name;
        this.context = context;
        this.time = time;
        this.tag = tag;
        this.status=status;
        this.isDelete = isDelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
