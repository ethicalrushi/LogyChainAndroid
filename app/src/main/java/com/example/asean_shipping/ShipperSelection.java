package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ShipperSelection extends AppCompatActivity {

    ArrayList<ShipperDataModel> dataModels;
    ListView listView;
    private static ShipperSelectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_selection);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();
        listView = (ListView) findViewById(R.id.listViewShipper);
        listView.setDivider(null);

        dataModels = receiveShippers();

        adapter = new ShipperSelectionAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShipperDataModel dataModel = dataModels.get(position);
            }
        });

    }

    public ArrayList<ShipperDataModel> receiveShippers(){
        ArrayList<ShipperDataModel> dataModels = new ArrayList<>();
        dataModels.add(new ShipperDataModel("FedEx", 5000.00));
        dataModels.add(new ShipperDataModel("DelhiVery", 4876.00));
        dataModels.add(new ShipperDataModel("InterShip", 5896.00));
        return dataModels;
    }
}