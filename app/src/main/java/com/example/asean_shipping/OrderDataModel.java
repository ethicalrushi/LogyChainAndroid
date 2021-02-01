package com.example.asean_shipping;

public class OrderDataModel {
        public int orderNumber;
        public int weight;
        public int numberOfPackages;
        public int cost;
        public String remarks;


    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
