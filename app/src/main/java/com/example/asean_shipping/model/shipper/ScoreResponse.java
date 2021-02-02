package com.example.asean_shipping.model.shipper;

import java.io.Serializable;

public class ScoreResponse implements Serializable {
    double creditScore;
    double balance;

    public double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(double creditScore) {
        this.creditScore = creditScore;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
