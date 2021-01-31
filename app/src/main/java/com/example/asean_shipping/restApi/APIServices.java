package com.example.asean_shipping.restApi;

import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ReportShipFromPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIServices {

    @POST("transaction/dummy/")
    Call<CreateShipmentGenericResponse> postShipFromData(@Body ReportShipFromPayload reportShipFromPayload);
}
