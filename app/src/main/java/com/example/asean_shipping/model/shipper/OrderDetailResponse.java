package com.example.asean_shipping.model.shipper;

import java.io.Serializable;

public class OrderDetailResponse implements Serializable {

    int orderNumber;
    int numberOfPackage;
    int weight;
    String additionalInformation;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getNumberOfPackage() {
        return numberOfPackage;
    }

    public void setNumberOfPackage(int numberOfPackage) {
        this.numberOfPackage = numberOfPackage;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
