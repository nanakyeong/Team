package com.example.primitive;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/light/sse")
    Call<String> makeRequest(@Body SetRequest setRequest);
    @GET("/light/control")
    Call<String> makeGetRequest(
           @Query("id") int id,
            @Query("cmd") String cmd,
            @Query("time") int time

    );
}
