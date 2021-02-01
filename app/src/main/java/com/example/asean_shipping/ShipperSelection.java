package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asean_shipping.model.auth.UserDetailResponse;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipperSelection extends AppCompatActivity {

    ArrayList<ShipperDataModel> dataModels;
    ListView listView;
    private static ShipperSelectionAdapter adapter;
    public String shipmentId;

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

        shipmentId = getIntent().getExtras().getString("shipmentId");

        receiveShippers();

        adapter = new ShipperSelectionAdapter(dataModels, getApplicationContext(), shipmentId);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShipperDataModel dataModel = dataModels.get(position);
            }
        });

    }

    public void receiveShippers(){

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.getShipmentAgency(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""))
                .enqueue(new Callback<ArrayList<ShipperDataModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ShipperDataModel>> call, Response<ArrayList<ShipperDataModel>> response) {
                        dataModels = response.body();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ShipperDataModel>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
    }
}