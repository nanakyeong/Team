package com.example.primitive;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/light/sse")
    Call<String> makeRequest
            (@Body SetRequest setRequest);
    @POST("/light/sse")
    Call<String> makeControlRequest(@Body ControlRequest controlRequest);
}







