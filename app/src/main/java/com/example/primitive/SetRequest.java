package com.example.primitive;

import com.google.gson.annotations.SerializedName;

public class SetRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("set")
    private int[] set;

    public SetRequest(int id, int[] set) {
        this.id = id;
        this.set = set;
    }
}




