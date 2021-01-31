package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

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

        debitAvail.setText("Wallet Balance");
        debitAvailBal.setText(getDebitAvailBal());

        creditAvail.setText("Credit Available");
        creditAvailBal.setText(getCreditAvailBal());
        
        Button shipPackage = (Button) findViewById(R.id.button);
        Button trackOrders = (Button) findViewById(R.id.button3);
        Button financials = (Button) findViewById(R.id.button5);
        
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
    }

    private String getCreditAvailBal() {
        return "1000.00 $";
    }


    private String getDebitAvailBal() {
        return "1000.00 $";
    }
}