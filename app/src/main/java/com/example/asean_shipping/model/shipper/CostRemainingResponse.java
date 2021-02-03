package com.example.asean_shipping.model.shipper;

import java.io.Serializable;

public class CostRemainingResponse implements Serializable {
    int cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
