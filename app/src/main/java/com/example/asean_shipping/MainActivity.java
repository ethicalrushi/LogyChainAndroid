package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.asean_shipping.fragments.CreateShipFrom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateShipFrom createShipFrom = new CreateShipFrom();
        createShipFrom.show(getSupportFragmentManager(),"createShipFrom");
//        Intent i  = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(i);
    }
}