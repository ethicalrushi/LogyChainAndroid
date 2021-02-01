package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.asean_shipping.fragments.CreateShipFrom;
import com.example.asean_shipping.fragments.CreateShipTo;
import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ReportShipFromToGenericPayload;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingActivity extends AppCompatActivity {
    public String shipmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<CreateShipmentGenericResponse> call = apiServices.getShipmentId(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""));

        call.enqueue(new Callback<CreateShipmentGenericResponse>() {
            @Override
            public void onResponse(Call<CreateShipmentGenericResponse> call, Response<CreateShipmentGenericResponse> response) {
                shipmentId = response.body().shipmentId;
                ReportShipFromToGenericPayload reportShipFromToGenericPayload = new ReportShipFromToGenericPayload();
                reportShipFromToGenericPayload.setShipmentId(shipmentId);
                APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
                Call<CreateShipmentGenericResponse> ncall = apiServices.postShipFromData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""),
                        reportShipFromToGenericPayload);

                ncall.enqueue(new Callback<CreateShipmentGenericResponse>() {
                    @Override
                    public void onResponse(Call<CreateShipmentGenericResponse> call, Response<CreateShipmentGenericResponse> response) {
                        CreateShipTo createShipTo= new CreateShipTo(response.body().shipmentId);
                        createShipTo.show(getSupportFragmentManager(),"createShipTo");
                    }

                    @Override
                    public void onFailure(Call<CreateShipmentGenericResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<CreateShipmentGenericResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}