package com.example.asean_shipping.restApi;

import com.example.asean_shipping.model.auth.LoginPayload;
import com.example.asean_shipping.model.auth.LoginResponse;
import com.example.asean_shipping.model.auth.SignupPayload;
import com.example.asean_shipping.model.auth.UserDetailResponse;
import com.example.asean_shipping.model.shipper.CreateShipmentGenericResponse;
import com.example.asean_shipping.model.shipper.ReportShipFromToGenericPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIServices {

    @GET("transaction/getShipmentId/")
    Call<CreateShipmentGenericResponse> getShipmentId(@Header("Authorization") String token);

    @POST("transaction/setShipFrom/")
    Call<CreateShipmentGenericResponse> postShipFromData(@Header("Authorization") String token, @Body ReportShipFromToGenericPayload reportShipFromToGenericPayload);

    @POST("transaction/setShipTo/")
    Call<CreateShipmentGenericResponse> postShipToData(@Header("Authorization") String token, @Body ReportShipFromToGenericPayload reportShipFromToGenericPayload);

    @POST("getToken/")
    Call<LoginResponse> sendLoginRequest(@Body LoginPayload loginPayload);

    @POST("user/")
    Call<SignupPayload> sendSignupRequest(@Body SignupPayload payload);

    @GET("user/getUserDetails/")
    Call<UserDetailResponse> getUserDetail(@Header("Authorization") String token);
}
