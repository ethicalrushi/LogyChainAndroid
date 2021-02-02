package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asean_shipping.fragments.CreateShipFrom;
import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ScoreResponse;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dashboard extends AppCompatActivity {
    double creditScore=0;
    double balance=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();
        TextView creditAvail = (TextView) findViewById(R.id.creditAvail);
        TextView creditAvailBal = (TextView) findViewById(R.id.creditAvailBal);
        TextView debitAvail = (TextView) findViewById(R.id.debitAvail);
        TextView debitAvailBal = (TextView) findViewById(R.id.debitAvailBal);

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.getScores(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""))
                .enqueue(new Callback<ScoreResponse>() {
                    @Override
                    public void onResponse(Call<ScoreResponse> call, Response<ScoreResponse> response) {
                        balance = response.body().getBalance();
                        creditScore = response.body().getCreditScore();

                        debitAvail.setText("Wallet Balance");
                        debitAvailBal.setText(Double.toString(balance));

                        creditAvail.setText("Credit Available");
                        creditAvailBal.setText(Double.toString(creditScore));
                    }

                    @Override
                    public void onFailure(Call<ScoreResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });



        Button shipPackage = (Button) findViewById(R.id.button);
        Button trackOrders = (Button) findViewById(R.id.button3);
        Button financials = (Button) findViewById(R.id.button5);
        Button scanQR = (Button) findViewById(R.id.qrscan);

        shipPackage.setOnClickListener(new Button.OnClickListener()
            {
                @Override
               public void onClick(View view)
                {
                    Intent i = new Intent(Dashboard.this, ShippingActivity.class);
                    startActivity(i);
                }
            });
        trackOrders.setOnClickListener(new Button.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent i = new Intent(Dashboard.this, TrackOrder.class);
                    startActivity(i);
                }
            });
        financials.setOnClickListener(new Button.OnClickListener()
            {
                @Override
                public void onClick(View view)
                    {
                        Intent i = new Intent(Dashboard.this, Financials.class);
                        startActivity(i);
                    }
            });
        scanQR.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Dashboard.this, scanQR.class);
                startActivity(i);
            }
        });
    }

}