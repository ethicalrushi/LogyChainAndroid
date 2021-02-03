package com.example.asean_shipping.model.shipper;

import java.io.Serializable;

public class PaymentResponse implements Serializable {
    boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
