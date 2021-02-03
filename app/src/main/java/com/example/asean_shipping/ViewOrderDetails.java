package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ViewOrderDetails extends AppCompatActivity {
    ArrayList<OrderDataModel> dataModels;
    ListView listView;
    private static OrderDetailsAdapter adapter;
    String shipmentId;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();
        shipmentId = getIntent().getExtras().getString(shipmentId);

        listView = (ListView) findViewById(R.id.detailsView);
        listView.setDivider(null);
        Button payment = (Button) findViewById(R.id.pay);

        dataModels = getOrderDetails();

        adapter = new OrderDetailsAdapter(dataModels, getApplicationContext(), shipmentId);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrderDataModel dataModel = dataModels.get(position);
            }
        });

        payment.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                LayoutInflater layoutInflater = (LayoutInflater) ViewOrderDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup,null);
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(listView, Gravity.CENTER, 0, 0);
                TextInputEditText privateKeyInput = (TextInputEditText) findViewById(R.id.privateKey);
                MaterialButton pay = (MaterialButton) findViewById(R.id.payBtn);
                final String[] privateKey = new String[1];
                pay.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        privateKey[0] = privateKeyInput.getText().toString();
                    }
                });
                processPayment(privateKey[0]);
            }
        });
    }

    public void processPayment(String privateKey){

    }

    public ArrayList<OrderDataModel> getOrderDetails(){
        ArrayList<OrderDataModel> dataModels = new ArrayList<>();
        dataModels.add(new OrderDataModel());
        return dataModels;
    }
}