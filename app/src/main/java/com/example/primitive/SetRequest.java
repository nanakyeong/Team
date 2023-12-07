package com.example.primitive;

import com.google.gson.annotations.SerializedName;

public class SetRequest {
    @SerializedName("illum")
    private int illum;

    @SerializedName("cct")
    private int cct;

    public SetRequest(int illum, int cct) {
        this.illum = illum;
        this.cct = cct;

    }
}




