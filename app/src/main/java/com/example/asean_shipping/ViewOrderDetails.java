package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.asean_shipping.model.shipper.PaymentResponse;
import com.example.asean_shipping.model.shipper.PrivateKeyPayload;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderDetails extends AppCompatActivity {
    ArrayList<OrderDataModel> dataModels;
    ListView listView;
    private static ViewOrderAdapter adapter;
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
        shipmentId = getIntent().getExtras().getString("shipmentId");

        listView = (ListView) findViewById(R.id.detailsView);
        listView.setDivider(null);
        Button payment = (Button) findViewById(R.id.pay);

        getOrderDetails();



        payment.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                LayoutInflater layoutInflater = (LayoutInflater) ViewOrderDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup,null);
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(listView, Gravity.CENTER, 0, 0);
                TextInputEditText privateKeyInput = (TextInputEditText) customView.findViewById(R.id.privateKey);
                privateKeyInput.setTextIsSelectable(true);
                MaterialButton pay = (MaterialButton) customView.findViewById(R.id.payBtn);
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
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        PrivateKeyPayload payload = new PrivateKeyPayload();
        payload.setPrivateKey(privateKey);
        payload.setShipmentId(shipmentId);

        APIServices apiServices1 = AppClient.getInstance().createService(APIServices.class);
        Call call = apiServices1.blockPayment(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""), payload);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                Toast.makeText(getApplicationContext(), "Order acknowledged and payment secured", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ViewOrderDetails.this, Dashboard.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Insufficient balance to secure the order", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getOrderDetails(){
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call call = apiServices.getOrderDetails(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""), shipmentId);
        call.enqueue(new Callback<List<OrderDataModel>>() {
            @Override
            public void onResponse(Call<List<OrderDataModel>> call, Response<List<OrderDataModel>> response) {
                dataModels = (ArrayList<OrderDataModel>) response.body();

                Log.v("size" ,"onResponse: "+Integer.toString(dataModels.size()));

                adapter = new ViewOrderAdapter(dataModels, getApplicationContext(), shipmentId);

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        OrderDataModel dataModel = dataModels.get(position);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<OrderDataModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}