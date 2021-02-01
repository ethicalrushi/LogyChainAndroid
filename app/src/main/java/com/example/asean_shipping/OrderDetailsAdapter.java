package com.example.asean_shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class OrderDetailsAdapter extends ArrayAdapter<OrderDataModel> implements View.OnClickListener {

    private ArrayList<OrderDataModel> dataSet;
    Context mContext;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextInputEditText orderID;
        TextInputEditText orderWeight;
        TextInputEditText boxes;
        TextInputEditText orderAmount;
        MaterialButton save;
    }

    public OrderDetailsAdapter(ArrayList<OrderDataModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
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
                int noBoxes = Integer.parseInt(viewHolder.boxes.getText().toString());
                orderDataModel.setNoBoxes(noBoxes);
                viewHolder.boxes.setEnabled(false);
                long orderId = Long.parseLong(viewHolder.orderID.getText().toString());
                orderDataModel.setOrderId(orderId);
                viewHolder.orderID.setEnabled(false);
                double orderWeight = Double.parseDouble(viewHolder.orderWeight.getText().toString());
                orderDataModel.setOrderWeight(orderWeight);
                viewHolder.orderWeight.setEnabled(false);
                double orderAmount = Double.parseDouble(viewHolder.orderAmount.getText().toString());
                viewHolder.orderAmount.setEnabled(false);
                orderDataModel.setOrderAmount(orderAmount);
                sendToAPI(orderDataModel);
            }
        });
        return convertView;
    }

    private void sendToAPI(OrderDataModel orderDataModel){

    }
}
