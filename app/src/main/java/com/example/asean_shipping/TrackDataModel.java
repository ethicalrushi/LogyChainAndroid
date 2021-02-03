package com.example.asean_shipping;

public class TrackDataModel {
    String shipmentId;
    String shipToName;
    String shipToCity;


    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipFromName() {
        return shipToName;
    }

    public void setShipFromName(String shipToName) {
        this.shipToName = shipToName;
    }
}
