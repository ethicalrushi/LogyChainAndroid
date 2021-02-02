package com.example.asean_shipping;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.SetShipmentAgencyPayload;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipperSelectionAdapter extends ArrayAdapter<ShipperDataModel> implements View.OnClickListener {

    private ArrayList<ShipperDataModel> dataSet;
    Context mContext;
    private int lastPosition = -1;
    public String shipmentId;

    private static class ViewHolder {
        TextInputEditText remarks;
        TextView cost;
        TextView shipperName;
        MaterialButton select;
    }

    public ShipperSelectionAdapter(ArrayList<ShipperDataModel> data, Context context, String shipmentId) {
        super(context, R.layout.row_item_shipper, data);
        this.dataSet = data;
        this.mContext = context;
        this.shipmentId = shipmentId;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShipperSelectionAdapter.ViewHolder viewHolder;
        final ShipperDataModel shipperDataModel = getItem(position);
        final View result;

        if (convertView == null) {

            viewHolder = new ShipperSelectionAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_shipper, parent, false);
            viewHolder.remarks = (TextInputEditText) convertView.findViewById(R.id.Remarks);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.amount);
            viewHolder.shipperName = (TextView) convertView.findViewById(R.id.ShipperName);
            viewHolder.select = (MaterialButton) convertView.findViewById(R.id.selectShipper);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShipperSelectionAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.select.setOnClickListener(new MaterialButton.OnClickListener(){
            @Override
            public void onClick(View v){
                contactAPItoSetOrder(shipperDataModel);
            }
        });
        return convertView;
    }

    public void contactAPItoSetOrder(ShipperDataModel shipperDataModel){
        SetShipmentAgencyPayload payload = new SetShipmentAgencyPayload();
        payload.setShipmentAgencyId(shipperDataModel.getPk());
        payload.setShipmentId(shipmentId);
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.setShipmentAgency(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", ""), payload)
                .enqueue(new Callback<CreateShipmentGenericResponse>() {
                    @Override
                    public void onResponse(Call<CreateShipmentGenericResponse> call, Response<CreateShipmentGenericResponse> response) {
                        Toast.makeText(getContext(), "Shipment Order placed", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(mContext, showQR.class);
                        intent.putExtra("shipmentId", shipmentId);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<CreateShipmentGenericResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
