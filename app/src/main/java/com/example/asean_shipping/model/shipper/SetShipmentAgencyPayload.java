package com.example.asean_shipping.model.shipper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetShipmentAgencyPayload {
    @SerializedName("contact")
    @Expose
    private int shipmentAgencyId;

    @SerializedName("shipmentId")
    @Expose
    private  String shipmentId;

    public int getShipmentAgencyId() {
        return shipmentAgencyId;
    }

    public void setShipmentAgencyId(int shipmentAgencyId) {
        this.shipmentAgencyId = shipmentAgencyId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
}
