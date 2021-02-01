package com.example.asean_shipping.model.auth;

import java.io.Serializable;

public class SignupPayload implements Serializable {
    public String name;
    public String companyName;
    public String password;
    public String contact;
    public String address="some address";
    public String city="some city";
    public String state="some state";
    public String role="some role";
    public String zipcode = "";
    public String publicAddress = "";
    public String licenseNumber="";
}