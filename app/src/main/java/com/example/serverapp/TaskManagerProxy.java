package com.example.serverapp;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2020, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: PersonManagerProxy
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2020/7/10 5:26 PM
 */
public class TaskManagerProxy implements ITaskManager {
    private IBinder iBinder;
    private static final String DESCRIPTOR = "com.example.serverapp.ITaskManager";
    public static ITaskManager sDefaultImpl;

    public TaskManagerProxy(IBinder iBinder) {
        this.iBinder = iBinder;
    }

    public String getInterfaceDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public void addTask(TaskInfo taskInfo) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (taskInfo != null) {
                data.writeInt(1);
                taskInfo.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            iBinder.transact(TaskManagerImpl.TRANSACTION_addTask, data, reply, 0);
            reply.readException();
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    @Override
    public void deleteTask(TaskInfo taskInfo) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (taskInfo != null) {
                data.writeInt(1);
                taskInfo.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            iBinder.transact(TaskManagerImpl.TRANSACTION_deleteTask, data, reply, 0);
            reply.readException();
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    @Override
    public List<TaskInfo> getTasks() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<TaskInfo> taskInfoList = null;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            boolean transact = iBinder.transact(TaskManagerImpl.TRANSACTION_getTasks, data, reply, 0);
            if (!transact && TaskManagerImpl.getDefaultImpl() != null) {
                return TaskManagerImpl.getDefaultImpl().getTasks();
            }
            reply.readException();
            if (reply.readInt() != 0) {
                taskInfoList = reply.createTypedArrayList(TaskInfo.CREATOR);
            }
        } finally {
            reply.recycle();
            data.recycle();
        }
        return taskInfoList;
    }

    @Override
    public IBinder asBinder() {
        return iBinder;
    }
}
