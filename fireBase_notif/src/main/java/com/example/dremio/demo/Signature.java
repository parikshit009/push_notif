package com.example.dremio.demo;

public class Signature {

   public String intimestamp;
    public String token;

    public Signature(String intimestamp, String token) {
        this.intimestamp = intimestamp;
        this.token = token;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("User{").append("intimestamp: ")
                .append(intimestamp).append(",token: ")
                .append(token).append("}").toString();
    }
}
