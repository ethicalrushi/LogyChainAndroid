package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackOrder extends AppCompatActivity {
    ArrayList<TrackDataModel> dataModels;
    ListView listView;
    private static TrackOrderAdapter adapter;
    public String shipmentId;
    TextInputEditText shipmentIdInputText;
    MaterialButton trackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();

        shipmentIdInputText = (TextInputEditText) findViewById(R.id.shipId);
        trackButton = (MaterialButton) findViewById(R.id.track);

        trackButton.setOnClickListener(new MaterialButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputShipmntId = shipmentIdInputText.getText().toString();
                Toast.makeText(getApplicationContext(), inputShipmntId, Toast.LENGTH_LONG).show();
            }
        });


        listView = (ListView) findViewById(R.id.trackList);
        listView.setDivider(null);


        receiveOrders();

    }

    public void receiveOrders(){
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call call = apiServices.getShipmentOrdersShipper(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""));
        call.enqueue(new Callback<List<TrackDataModel>>() {
            @Override
            public void onResponse(Call<List<TrackDataModel>> call, Response<List<TrackDataModel>> response) {
                dataModels = (ArrayList<TrackDataModel>) response.body();
                adapter = new TrackOrderAdapter(dataModels, getApplicationContext());

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TrackDataModel dataModel = dataModels.get(position);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<TrackDataModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
}