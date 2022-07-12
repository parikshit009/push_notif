package com.example.dremio.demo;

public class DremioResponse {

    public DremioEmail[] row;

    public DremioResponse(DremioEmail[] row)
    {
        this.row=row;
    }
}
