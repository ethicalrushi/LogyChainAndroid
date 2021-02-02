package com.example.asean_shipping.model.shipper;

import java.io.Serializable;

public class ScanDetailsResponse implements Serializable {
    String shipFromName;
    String shipToName;
    String shipToCity;
    int weight;
    int packages;
    String shipmentId;
    String suggestions;

    public String getShipFromName() {
        return shipFromName;
    }

    public void setShipFromName(String shipFromName) {
        this.shipFromName = shipFromName;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPackages() {
        return packages;
    }

    public void setPackages(int packages) {
        this.packages = packages;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }
}
