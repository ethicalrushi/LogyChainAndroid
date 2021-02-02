package com.example.asean_shipping;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ScanDetailsResponse;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostScanConfirmationActivity extends AppCompatActivity {

    TextView shipFromName, shipToName, shipToCity, weight, numberOfPackages, suggestions;
    TextInputEditText remarks;
    Button accept, reject;
    String shipmentId, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_details_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();

        shipmentId = getIntent().getExtras().getString("shipmentId");
        latitude = getIntent().getExtras().getString("latitude");
        longitude = getIntent().getExtras().getString("longitude");

        shipFromName = (TextView) findViewById(R.id.scanShipFrom);
        shipToName = (TextView) findViewById(R.id.scanShipTo);
        shipToCity = (TextView) findViewById(R.id.scanShipToCity);
        weight = (TextView) findViewById(R.id.scanWeight);
        numberOfPackages = (TextView) findViewById(R.id.scanNumberOfPackages);
        suggestions = (TextView) findViewById(R.id.scanSuggestions);
        remarks = (TextInputEditText) findViewById(R.id.scanRemarks);

        accept = (Button) findViewById(R.id.scanApproveBtn);
        reject = (Button) findViewById(R.id.scanDisapproveBtn);

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.getScanDetails(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""), shipmentId)
                .enqueue(new Callback<ScanDetailsResponse>() {
                    @Override
                    public void onResponse(Call<ScanDetailsResponse> call, Response<ScanDetailsResponse> response) {
                        shipFromName.setText("From: "+response.body().getShipFromName());
                        shipToName.setText("To: "+response.body().getShipToName());
                        shipToCity.setText("Destination: "+response.body().getShipToCity());
                        weight.setText("Weight: "+Integer.toString(response.body().getWeight()));
                        numberOfPackages.setText("Number of Packages: "+Integer.toString(response.body().getPackages()));
                    }

                    @Override
                    public void onFailure(Call<ScanDetailsResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });




    }
}
