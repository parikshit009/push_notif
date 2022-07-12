package com.example.dremio.demo;

import java.util.ArrayList;

public class GApiBody {

    public String to;
    public ArrayList<String> registration_tokens;


//    public Google_API_Body(String to, ArrayList<String> registration_tokens) {
//        this.to = to;
//        this.registration_tokens = registration_tokens;
//    }

    public GApiBody(String to, ArrayList<String> registration_tokens) {
        this.to = to;
        this.registration_tokens = registration_tokens;
    }

}
