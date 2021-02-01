package com.example.asean_shipping;

public class ShipperDataModel {
    public String shipperName;
    public double cost;
    public String remarks = "";

    public ShipperDataModel(String shipperName, double cost){
        this.shipperName = shipperName;
        this.cost = cost;
    }

    public void setRemarks(String remarks){
        this.remarks = remarks;
    }
}
