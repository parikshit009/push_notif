package com.example.dremio.demo;

public class ProdApiResponse {

    public String email;
    public String token;
    public String deviceType;

    public ProdApiResponse(String email, String token, String deviceType) {
        this.email = email;
        this.token = token;
        this.deviceType=deviceType;
    }
}
