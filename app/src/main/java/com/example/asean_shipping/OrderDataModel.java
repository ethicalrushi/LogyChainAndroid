package com.example.asean_shipping;

public class OrderDataModel {

        public long orderId = 0;
        public double orderWeight = 0.0;
        public int noBoxes = 0;
        public double orderAmount = 0.0;

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public void setOrderWeight(double orderWeight) {
            this.orderWeight = orderWeight;
        }

        public void setNoBoxes(int noBoxes){
            this.noBoxes = noBoxes;
        }

        public void setOrderAmount(double orderAmount){
            this.orderAmount = orderAmount;
        }


}
