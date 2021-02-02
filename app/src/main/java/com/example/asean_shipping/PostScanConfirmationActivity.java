package com.example.asean_shipping;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ReportTrackDataPayload;
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
    Button accept, reject, viewBol;
    String shipmentId, latitude, longitude;
    Boolean receiverFlag=false;

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
        viewBol = (Button) findViewById(R.id.scanViewBol);

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
                        receiverFlag = response.body().isReceiver();
                    }

                    @Override
                    public void onFailure(Call<ScanDetailsResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });

        accept.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remarkText = remarks.getText().toString();
                boolean approved = true;

                if(receiverFlag) {
                    //call new intent for final payment and pass below values and set track data their
                }

                ReportTrackDataPayload payload = new ReportTrackDataPayload();
                payload.setApproved(approved);
                payload.setLatitude(latitude);
                payload.setLongitude(longitude);
                payload.setShipmentId(shipmentId);
                payload.setRemarks(remarkText);

                sendTrackData(payload);
            }
        });

        reject.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remarkText = remarks.getText().toString();
                boolean approved = false;

                ReportTrackDataPayload payload = new ReportTrackDataPayload();
                payload.setApproved(approved);
                payload.setLatitude(latitude);
                payload.setLongitude(longitude);
                payload.setShipmentId(shipmentId);
                payload.setRemarks(remarkText);

                sendTrackData(payload);

            }
        });

        viewBol.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostScanConfirmationActivity.this, BOLActivity.class);
                i.putExtra("shipmentId",shipmentId);
                startActivity(i);
            }
        });




    }

    public void sendTrackData(ReportTrackDataPayload payload) {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.setTrackingData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""), payload)
                .enqueue(new Callback<CreateShipmentGenericResponse>() {
                    @Override
                    public void onResponse(Call<CreateShipmentGenericResponse> call, Response<CreateShipmentGenericResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(getApplicationContext(), "Tracking Info sent", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PostScanConfirmationActivity.this, Dashboard.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateShipmentGenericResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PostScanConfirmationActivity.this, Dashboard.class);
                        startActivity(intent);
                    }
                });
    }
}
