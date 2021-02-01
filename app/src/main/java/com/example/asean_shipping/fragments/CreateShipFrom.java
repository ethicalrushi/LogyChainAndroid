package com.example.asean_shipping.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.asean_shipping.R;
import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ReportShipFromToGenericPayload;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateShipFrom extends DialogFragment {
    private Button shipFromSubmit;
    private TextInputEditText shipFromName, shipFromContact, shipFromAddress, shipFromCity, shipFromState, shipFromZip;
    private TextView shipFromToGenericTitle;
    private String shipmentId;

    public CreateShipFrom() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(width, height);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_ship_from_to_generic, container, false);

        shipFromToGenericTitle = view.findViewById(R.id.create_ship_from_to_detail_title);
        shipFromToGenericTitle.setText("Enter Shipper's Details");
        shipFromContact = view.findViewById(R.id.shipFromContact);
        shipFromSubmit = view.findViewById(R.id.create_ship_from_proceed_btn);

//        view.findViewById(R.id.farmer_report_prod_back).setOnClickListener(view1 -> dismiss());

        shipFromSubmit.setOnClickListener(v -> {

            ReportShipFromToGenericPayload reportShipFromToGenericPayload = new ReportShipFromToGenericPayload();
            reportShipFromToGenericPayload.setContact(shipFromContact.getText().toString());
            reportShipFromToGenericPayload.setShipmentId(shipmentId);



        });

        return view;
    }
}
