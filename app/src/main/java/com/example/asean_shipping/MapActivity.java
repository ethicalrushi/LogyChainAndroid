package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asean_shipping.model.shipper.GetTrackingDataResponse;
import com.example.asean_shipping.model.shipper.ScanDetailsResponse;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    String shipmentId;
    ArrayList<GetTrackingDataResponse> dataObjects;
    TextView trackDetails;
    Button viewBol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();

        shipmentId = getIntent().getExtras().getString("shipmentId");

        trackDetails = (TextView) findViewById(R.id.trackDetails);
        viewBol = (Button) findViewById(R.id.mapViewBol);

        viewBol.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, BOLActivity.class);
                i.putExtra("shipmentId", shipmentId);
                startActivity(i);
            }
        });


        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.getTrackingData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""), shipmentId)
                .enqueue(new Callback<List<GetTrackingDataResponse>>() {
                    @Override
                    public void onResponse(Call<List<GetTrackingDataResponse>> call, Response<List<GetTrackingDataResponse>> response) {
                        dataObjects = (ArrayList<GetTrackingDataResponse>) response.body();
                        Iterator<GetTrackingDataResponse> iter= dataObjects.iterator();
                        String message = "";
                        while (iter.hasNext()){
                            GetTrackingDataResponse data = iter.next();
                            String prefix = "Shipment Broken : ";
                            if(data.approved) {
                                prefix = "Shipment Secure : ";
                            }
                            try {
                                message = message + "\n\n" + prefix+"The package was received by " + data.companyName + " on "+ data.date + " at " + data.time + " at Location:" + getAddress(data.latitude, data.longitude);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if(dataObjects.size()==0) {
                            message = "The package is yet not received by any agency";
                        }
                        trackDetails.setText(message);
                    }

                    @Override
                    public void onFailure(Call<List<GetTrackingDataResponse>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), " api something went wrong", Toast.LENGTH_LONG).show();
                    }
                });


    }

    public String getAddress(String lat, String lng) throws IOException {
        String city, state, country;
        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);
        if (addresses.size() > 0) {
            city = addresses.get(0).getAddressLine(0);
            return city;
        }
        else {
            return "NA, NA";
        }
    }

    private void getDataObjects() {

//        DataObject dataObject = new DataObject();
//        dataObject.latitude = "27.176670";
//        dataObject.longitude = "78.008072";
//        dataObject.remark = "None";
//        dataObject.date = "01022021";
//        dataObject.time = "15:30 GMT";
//        dataObject.companyName = "ASEAN";

//        dataObjects.add(dataObject);
    }
}