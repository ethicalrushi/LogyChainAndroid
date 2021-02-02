package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asean_shipping.model.auth.UserDetailResponse;
import com.example.asean_shipping.model.shipper.ShipmentAgencyListResponse;
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



    }

    public void receiveShippers(){

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call call = apiServices.getShipmentAgency(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""));
                call.enqueue(new Callback<ShipmentAgencyListResponse>() {
                    @Override
                    public void onResponse(Call<ShipmentAgencyListResponse> call, Response<ShipmentAgencyListResponse> response) {
                        Log.e("shipmentAgency", "onResponse: "+response.body().data.get(0).name);
                        dataModels = response.body().data;
                        adapter = new ShipperSelectionAdapter(dataModels, getApplicationContext(), shipmentId);

                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                ShipperDataModel dataModel = dataModels.get(position);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ShipmentAgencyListResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
    }
}