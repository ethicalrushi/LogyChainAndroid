package com.example.asean_shipping.model.shipper;

import com.example.asean_shipping.ShipperDataModel;

import java.io.Serializable;
import java.util.List;

public class ShipmentAgencyListResponse implements Serializable {
    List<ShipperDataModel> data;
}
