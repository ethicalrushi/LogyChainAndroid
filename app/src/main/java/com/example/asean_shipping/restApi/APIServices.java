package com.example.asean_shipping.restApi;

import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ReportShipFromToGenericPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIServices {

    @POST("transaction/dummy/")
    Call<CreateShipmentGenericResponse> postShipFromData(@Body ReportShipFromToGenericPayload reportShipFromToGenericPayload);

    @POST("transaction/dummy/")
    Call<CreateShipmentGenericResponse> postShipToData(@Body ReportShipFromToGenericPayload reportShipFromToGenericPayload);
}
