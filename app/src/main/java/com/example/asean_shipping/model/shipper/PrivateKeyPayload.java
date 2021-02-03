package com.example.asean_shipping.model.shipper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivateKeyPayload {
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @SerializedName("shipmentId")
    @Expose
    String shipmentId;

    @SerializedName("privateKey")
    @Expose
    String privateKey;
}
