package com.example.primitive;

import com.google.gson.annotations.SerializedName;

public class ControlRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("time")
    private int time;

    public ControlRequest(int id, String cmd, int time) {
        this.id = id;
        this.cmd = cmd;
        this.time = time;
    }
}
