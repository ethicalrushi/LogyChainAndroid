package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PendingOrders extends AppCompatActivity {

    ListView listView;
    private static PendingOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();

        listView = (ListView) findViewById(R.id.pendingList);
        listView.setDivider(null);


        ArrayList<TrackDataModel> dataModels = receiveOrders();
        adapter = new PendingOrderAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TrackDataModel dataModel = dataModels.get(position);
            }
        });
    }

    public ArrayList<TrackDataModel> receiveOrders(){
        ArrayList<TrackDataModel> dataModels = new ArrayList<>();
        return dataModels;
    }
}