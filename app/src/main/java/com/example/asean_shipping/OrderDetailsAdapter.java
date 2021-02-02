package com.example.asean_shipping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.asean_shipping.model.auth.UserDetailResponse;
import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsAdapter extends ArrayAdapter<OrderDataModel> implements View.OnClickListener {

    private ArrayList<OrderDataModel> dataSet;
    Context mContext;
    private int lastPosition = -1;
    private String shipmentId;
    private Uri fileUri;
    private String filePath;
    public static final int PICKFILE_RESULT_CODE = 15;

    private static class ViewHolder {
        MaterialButton addFiles;
        TextInputEditText orderID;
        TextInputEditText orderWeight;
        TextInputEditText boxes;
        TextInputEditText orderAmount;
        TextInputEditText orderRemarks;
        MaterialButton save;
    }

    public OrderDetailsAdapter(ArrayList<OrderDataModel> data, Context context, String shipmentId) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.shipmentId = shipmentId;
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final OrderDataModel orderDataModel = getItem(position);
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.orderID = (TextInputEditText) convertView.findViewById(R.id.orderId);
            viewHolder.orderWeight = (TextInputEditText) convertView.findViewById(R.id.orderWeight);
            viewHolder.boxes = (TextInputEditText) convertView.findViewById(R.id.noBoxes);
            viewHolder.orderAmount = (TextInputEditText) convertView.findViewById(R.id.orderAmount);
            viewHolder.orderRemarks = (TextInputEditText) convertView.findViewById(R.id.orderRemarks);
            viewHolder.save = (MaterialButton) convertView.findViewById(R.id.save);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.save.setOnClickListener(new MaterialButton.OnClickListener(){
            @Override
            public void onClick(View v){
                orderDataModel.setShipmentId(shipmentId);
                int noBoxes = Integer.parseInt(viewHolder.boxes.getText().toString());
                orderDataModel.setNumberOfPackages(noBoxes);
                viewHolder.boxes.setEnabled(false);
                int orderId = Integer.parseInt(viewHolder.orderID.getText().toString());
                orderDataModel.setOrderNumber(orderId);
                viewHolder.orderID.setEnabled(false);
                int orderWeight = Integer.parseInt(viewHolder.orderWeight.getText().toString());
                orderDataModel.setWeight(orderWeight);
                viewHolder.orderWeight.setEnabled(false);
                int orderAmount = Integer.parseInt(viewHolder.orderAmount.getText().toString());
                viewHolder.orderAmount.setEnabled(false);
                orderDataModel.setCost(orderAmount);
                String orderRemarks = viewHolder.orderRemarks.getText().toString();
                viewHolder.orderRemarks.setEnabled(false);
                orderDataModel.setRemarks(orderRemarks);
                sendToAPI(orderDataModel);
            }
        });

        return convertView;
    }


    private void sendToAPI(OrderDataModel orderDataModel){
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.setCustomerOrder(PreferenceManager.getDefaultSharedPreferences(mContext).getString("token", ""), orderDataModel)
                .enqueue(new Callback<CreateShipmentGenericResponse>() {
                    @Override
                    public void onResponse(Call<CreateShipmentGenericResponse> call, Response<CreateShipmentGenericResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(mContext, "Order sent", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateShipmentGenericResponse> call, Throwable t) {
                        Toast.makeText(mContext, "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
