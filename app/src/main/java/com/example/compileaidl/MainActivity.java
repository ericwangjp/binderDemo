package com.example.compileaidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.serverapp.ITaskManager;
import com.example.serverapp.TaskInfo;
import com.example.serverapp.TaskManagerImpl;

import java.util.List;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btnAdd, btnDelete, btnGetAll;
    private ITaskManager iTaskManager;
    private boolean isBound;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: " + name);
            iTaskManager = TaskManagerImpl.asInterface(service);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: " + name);
            iTaskManager = null;
            isBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnGetAll = findViewById(R.id.btn_get_all);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnGetAll.setOnClickListener(this);

        Intent intent = new Intent("com.example.serveraidl");
        intent.setComponent(new ComponentName("com.example.serverapp", "com.example.serverapp.TaskService"));
        boolean bindService = bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        Log.e(TAG, "bindService: " + bindService);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                TaskInfo taskInfo = new TaskInfo("task1", 100);
                try {
                    iTaskManager.addTask(taskInfo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_delete:
                TaskInfo taskInfo2 = new TaskInfo("task2", 200);
                try {
                    iTaskManager.deleteTask(taskInfo2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_get_all:
                try {
                    List<TaskInfo> tasks = iTaskManager.getTasks();
                    Log.e(TAG, "onClick: " + tasks.size());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound){
            unbindService(serviceConnection);
        }
    }
}
