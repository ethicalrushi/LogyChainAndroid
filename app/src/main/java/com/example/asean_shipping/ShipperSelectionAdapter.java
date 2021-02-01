package com.example.asean_shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ShipperSelectionAdapter extends ArrayAdapter<ShipperDataModel> implements View.OnClickListener {

    private ArrayList<ShipperDataModel> dataSet;
    Context mContext;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextInputEditText remarks;
        TextView cost;
        TextView shipperName;
        MaterialButton select;
    }

    public ShipperSelectionAdapter(ArrayList<ShipperDataModel> data, Context context) {
        super(context, R.layout.row_item_shipper, data);
        this.dataSet = data;
        this.mContext = context;
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
                String remarks = viewHolder.remarks.getText().toString();
//                ShipperDataModel.setRemarks(remarks);
                contactAPItoSetOrder();
            }
        });
        return convertView;
    }

    public void contactAPItoSetOrder(){

    }
}
