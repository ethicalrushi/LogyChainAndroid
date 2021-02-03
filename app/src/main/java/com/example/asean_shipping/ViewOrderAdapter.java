package com.example.asean_shipping;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ViewOrderAdapter extends ArrayAdapter<OrderDataModel> implements View.OnClickListener {
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

    public ViewOrderAdapter(ArrayList<OrderDataModel> data, Context context, String shipmentId) {
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
        ViewOrderAdapter.ViewHolder viewHolder;
        final OrderDataModel orderDataModel = getItem(position);
        final View result;

        if (convertView == null) {

            viewHolder = new ViewOrderAdapter.ViewHolder();
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
            viewHolder = (ViewOrderAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.boxes.setText(orderDataModel.getNumberOfPackages());
        viewHolder.boxes.setEnabled(false);
        viewHolder.orderID.setText(orderDataModel.getOrderNumber());
        viewHolder.orderID.setEnabled(false);
        viewHolder.orderWeight.setText(orderDataModel.getWeight());
        viewHolder.orderWeight.setEnabled(false);
        viewHolder.orderAmount.setText(orderDataModel.getCost());
        viewHolder.orderAmount.setEnabled(false);
        viewHolder.orderRemarks.setText(orderDataModel.getRemarks());
        viewHolder.orderRemarks.setEnabled(false);
        viewHolder.save.setEnabled(false);

        return convertView;
    }

}
