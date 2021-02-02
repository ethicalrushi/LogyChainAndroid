package com.example.asean_shipping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class TrackOrderAdapter extends ArrayAdapter<TrackDataModel> implements View.OnClickListener {

    private ArrayList<TrackDataModel> dataSet;
    Context mContext;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView toContact;
        TextView shipId;
        MaterialButton track;
        MaterialButton showqr;
    }

    public TrackOrderAdapter(ArrayList<TrackDataModel> data, Context context) {
        super(context, R.layout.row_item_track_order, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackOrderAdapter.ViewHolder viewHolder;
        final TrackDataModel trackDataModel = getItem(position);
        final View result;

        if (convertView == null) {

            viewHolder = new TrackOrderAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_track_order, parent, false);
            viewHolder.toContact = (TextView) convertView.findViewById(R.id.toContact);
            viewHolder.shipId = (TextView) convertView.findViewById(R.id.ShipID);
            viewHolder.track = (MaterialButton) convertView.findViewById(R.id.trackOrderbtn);
            viewHolder.showqr = (MaterialButton) convertView.findViewById(R.id.showqr);
            result=convertView;

            viewHolder.toContact.setText("Shipping To: "+trackDataModel.getShipToName());
            viewHolder.shipId.setText("ShipmentID: "+trackDataModel.getShipmentId());
            //put city also here

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TrackOrderAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.track.setOnClickListener(new MaterialButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(mContext, MapActivity.class);
                i.putExtra("shipmentId", trackDataModel.getShipmentId());
            }
        });
        viewHolder.showqr.setOnClickListener(new MaterialButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(mContext, showQR.class);
                i.putExtra("shipmentId", trackDataModel.getShipmentId());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
        return convertView;
    }
}
