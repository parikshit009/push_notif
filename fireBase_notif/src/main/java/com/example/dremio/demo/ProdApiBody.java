package com.example.dremio.demo;

import java.util.ArrayList;

public class ProdApiBody {

    public String deviceType;
    public ArrayList<String> emailIds;


    public ProdApiBody(String deviceType, ArrayList<String> emailIds) {
        this.deviceType = deviceType;
        this.emailIds = emailIds;
    }

}
