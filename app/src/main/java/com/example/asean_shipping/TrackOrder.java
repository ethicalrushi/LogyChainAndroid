package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TrackOrder extends AppCompatActivity {
    ArrayList<TrackDataModel> dataModels;
    ListView listView;
    private static TrackOrderAdapter adapter;
    public String shipmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();

        shipmentId = getIntent().getExtras().getString("shipmentId");
        listView = (ListView) findViewById(R.id.listViewShipper);
        listView.setDivider(null);

        shipmentId = getIntent().getExtras().getString("shipmentId");

        dataModels = receiveOrders();

        adapter = new TrackOrderAdapter(dataModels, getApplicationContext(), shipmentId);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TrackDataModel dataModel = dataModels.get(position);
            }
        });

    }

    public ArrayList<TrackDataModel> receiveOrders(){
        ArrayList<TrackDataModel> dataModels = new ArrayList<>();
        TrackDataModel dataModel = new TrackDataModel();
        dataModel.setShipmentId("12345");
        dataModel.setToContact("8057430464");
        dataModels.add(dataModel);
        return dataModels;
    }
}