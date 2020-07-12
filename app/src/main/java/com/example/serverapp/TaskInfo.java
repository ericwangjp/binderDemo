package com.example.serverapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2020, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: TaskInfo
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2020/7/10 4:54 PM
 */
public class TaskInfo implements Parcelable {
    public String name;
    public int id;

    public TaskInfo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected TaskInfo(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<TaskInfo> CREATOR = new Creator<TaskInfo>() {
        @Override
        public TaskInfo createFromParcel(Parcel in) {
            return new TaskInfo(in);
        }

        @Override
        public TaskInfo[] newArray(int size) {
            return new TaskInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }
}
