package com.example.asean_shipping.model.auth;

import java.io.Serializable;

public class UserDetailResponse implements Serializable {
    public String role;
    public String name;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
