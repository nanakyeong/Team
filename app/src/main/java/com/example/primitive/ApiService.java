package com.example.primitive;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/light")
    Call<String> makeRequest(
            @Query("id") int id,
            @Query("time") int time,
            @Query("cmd") String cmd
    );
}
