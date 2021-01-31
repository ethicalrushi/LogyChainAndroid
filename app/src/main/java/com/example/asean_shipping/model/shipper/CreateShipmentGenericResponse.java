package com.example.asean_shipping.model.shipper;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CreateShipmentGenericResponse implements Serializable {
    public String shipmentId;

    @NonNull
    @Override
    public String toString() {
        return "Shipment ID: "+shipmentId;
    }
}
