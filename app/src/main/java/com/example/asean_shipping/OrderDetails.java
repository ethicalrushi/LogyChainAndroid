package com.example.asean_shipping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {

    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 16;
    ArrayList<OrderDataModel> dataModels;
    ListView listView;
    private static OrderDetailsAdapter adapter;
    public String shipmentId;
    public static final int PICKFILE_RESULT_CODE = 15;
    private Uri fileUri;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#006994"));
        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(OrderDetails.this, new
                String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setDivider(null);

        shipmentId = getIntent().getExtras().getString("shipmentId");
        (shipmentId+" ").trim();

        dataModels = new ArrayList<>();
        dataModels.add(new OrderDataModel());

        Button addOrder = (Button) findViewById(R.id.addOrder);
        Button saveOrder = (Button) findViewById(R.id.submitOrder);
        Button addFiles = (Button) findViewById(R.id.addFiles);

        adapter = new OrderDetailsAdapter(dataModels, getApplicationContext(), shipmentId);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrderDataModel dataModel = dataModels.get(position);
            }
        });

        addOrder.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                dataModels.add(new OrderDataModel());
            }
        });

        saveOrder.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(OrderDetails.this, ShipperSelection.class);
                i.putExtra("shipmentId", shipmentId);
                startActivity(i);
            }
        });

        addFiles.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    File file = new File(fileUri.getPath());
                    uploadFile(file, shipmentId);
                }

                break;
        }
    }

    public void uploadFile(File file, String shipmentId) {

    }


}