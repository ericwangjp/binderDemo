package com.example.serverapp;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2020, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: PersonManager
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2020/7/10 4:52 PM
 */
public interface ITaskManager extends IInterface {
    static final int TRANSACTION_addTask = (IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_deleteTask = (IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_getTasks = (IBinder.FIRST_CALL_TRANSACTION + 2);

    void addTask(TaskInfo taskInfo) throws RemoteException;

    void deleteTask(TaskInfo taskInfo) throws RemoteException;

    List<TaskInfo> getTasks() throws RemoteException;

    public static class Default implements ITaskManager {

        @Override
        public void addTask(TaskInfo taskInfo) throws RemoteException {

        }

        @Override
        public void deleteTask(TaskInfo taskInfo) throws RemoteException {

        }

        @Override
        public List<TaskInfo> getTasks() throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
