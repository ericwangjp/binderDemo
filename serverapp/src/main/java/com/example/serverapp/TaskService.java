package com.example.serverapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2020, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: TaskService
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2020/7/11 6:38 PM
 */
public class TaskService extends Service {
    private static final String TAG = "TaskService";
    private static final List<TaskInfo> TASK_INFOS = new ArrayList<>();
    private static final String CHANNEL_ID = "task_manager";
    private static final int NOTIFICATION_ID = 1;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: "+"TaskService启动" );
        createNotificationChannel();
        showNotification();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "通知渠道名称", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("渠道描述");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification() {
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText("当前任务：" + TASK_INFOS.toString());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("任务管理正在进行中")
                .setStyle(bigTextStyle)
//                不设置小图标，文字可能不显示
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(NOTIFICATION_ID, builder.build());

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteBinder();
    }

    class RemoteBinder extends TaskManagerImpl {

        @Override
        public void addTask(TaskInfo taskInfo) throws RemoteException {
            Log.e(TAG, "addTask: " + taskInfo.name);
            if (taskInfo != null) {
                TASK_INFOS.add(taskInfo);
                showNotification();
            }
        }

        @Override
        public void deleteTask(TaskInfo taskInfo) throws RemoteException {
            Log.e(TAG, "deleteTask: " + taskInfo.name);
            TASK_INFOS.remove(taskInfo);
            showNotification();
        }

        @Override
        public List<TaskInfo> getTasks() throws RemoteException {
            Log.e(TAG, "getTasks: ");
            ArrayList<TaskInfo> taskInfos = new ArrayList<>();
            taskInfos.add(new TaskInfo("jack", 10));
            taskInfos.add(new TaskInfo("tom", 20));
            return taskInfos;
        }
    }
}
