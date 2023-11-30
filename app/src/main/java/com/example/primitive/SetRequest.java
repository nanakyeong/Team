package com.example.primitive;

import com.google.gson.annotations.SerializedName;

public class SetRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("illum")
    private int illum;

    @SerializedName("temp")
    private int temp;

    public SetRequest(int id, int illum, int temp) {
        this.id = id;
        this.illum = illum;
        this.temp = temp;

    }
}




