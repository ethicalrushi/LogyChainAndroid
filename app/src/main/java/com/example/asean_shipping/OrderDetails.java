package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {

    ArrayList<OrderDataModel> dataModels;
    ListView listView;
    private static OrderDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setDivider(null);

        dataModels = new ArrayList<>();
        dataModels.add(new OrderDataModel());

        Button addOrder = (Button) findViewById(R.id.addOrder);
        Button saveOrder = (Button) findViewById(R.id.submitOrder);

        adapter = new OrderDetailsAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrderDataModel dataModel = dataModels.get(position);
            }
        });

        addOrder.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                dataModels.add(new OrderDataModel());
            }
        });

        saveOrder.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(OrderDetails.this, ShipperSelection.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("OrderDetails", dataModels);
                startActivity(i);
            }
        });
    }
}