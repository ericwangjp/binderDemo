package com.example.serverapp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2020, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: TaskManagerImpl
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2020/7/11 4:27 PM
 */
public abstract class TaskManagerImpl extends Binder implements ITaskManager {
    private static final String DESCRIPTOR = "com.example.serverapp.ITaskManager";
    private static final String TAG = "TaskManagerImpl";

    public TaskManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static ITaskManager asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof ITaskManager) {
            return (ITaskManager) iInterface;
        }
        return new TaskManagerProxy(iBinder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_addTask:
                data.enforceInterface(DESCRIPTOR);
                TaskInfo taskInfo = null;
                if (data.readInt() != 0) {
                    taskInfo = TaskInfo.CREATOR.createFromParcel(data);
                }
                this.addTask(taskInfo);
                reply.writeNoException();
                return true;
            case TRANSACTION_deleteTask:
                data.enforceInterface(DESCRIPTOR);
                TaskInfo deleteTaskInfo = null;
                if (data.readInt() != 0) {
                    deleteTaskInfo = TaskInfo.CREATOR.createFromParcel(data);
                }
                this.deleteTask(deleteTaskInfo);
                reply.writeNoException();
                return true;
            case TRANSACTION_getTasks:
                data.enforceInterface(DESCRIPTOR);
                List<TaskInfo> taskInfoList = this.getTasks();
                reply.writeNoException();
                reply.writeTypedList(taskInfoList);
                return true;
            default:
                return super.onTransact(code, data, reply, flags);
        }
    }


    public static boolean setDefaultImpl(ITaskManager iTaskManager) {
        if (TaskManagerProxy.sDefaultImpl == null && iTaskManager != null) {
            TaskManagerProxy.sDefaultImpl = iTaskManager;
            return true;
        }
        return false;
    }

    public static ITaskManager getDefaultImpl() {
        return TaskManagerProxy.sDefaultImpl;
    }
}
