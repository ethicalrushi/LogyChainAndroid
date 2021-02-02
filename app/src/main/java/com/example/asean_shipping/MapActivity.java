package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {

    public class DataObject{
        String date;
        String time;
        String remark;
        String latitude;
        String longitude;
        String companyName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();
        ArrayList<DataObject> dataObjects= new ArrayList<>();
        dataObjects = getDataObjects();
        Iterator<DataObject> iter= dataObjects.iterator();
        TextView trackDetails = (TextView) findViewById(R.id.trackDetails);
        String message = "";
        while (iter.hasNext()){
            DataObject data = iter.next();
            try {
                message = message + "\n\n" + "The package was received by " + data.companyName + " on "+ data.date + " at " + data.time + " at Location:" + getAddress(data.latitude, data.longitude);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        trackDetails.setText(message);
    }

    public String getAddress(String lat, String lng) throws IOException {
        String city, state, country;
        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);
        if (addresses.size() > 0) {
            city = addresses.get(0).getAddressLine(0);
            state = addresses.get(0).getAddressLine(1);
            country = addresses.get(0).getAddressLine(2);
            return (city + ", " + country);
        }
        else {
            return "NA, NA";
        }
    }

    private ArrayList<DataObject> getDataObjects() {
        ArrayList<DataObject> dataObjects= new ArrayList<>();
        DataObject dataObject = new DataObject();
        dataObject.latitude = "27.176670";
        dataObject.longitude = "78.008072";
        dataObject.remark = "None";
        dataObject.date = "01022021";
        dataObject.time = "15:30 GMT";
        dataObject.companyName = "ASEAN";

        dataObjects.add(dataObject);
        return dataObjects;
    }
}