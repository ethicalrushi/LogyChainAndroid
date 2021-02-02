package com.example.asean_shipping;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public abstract class BaseActivity extends AppCompatActivity {

    protected BroadcastReceiver receiver;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSupportActionBar().hide();
        ActivityCompat.requestPermissions(BaseActivity.this, new
                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    protected abstract int getLayoutResID();

    @Override
    protected void onResume() {
        super.onResume();
//        receiver = new NetworkChangeReceiver();
//
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGED");
//        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//            receiver = null;
//        }

        super.onDestroy();
    }

}