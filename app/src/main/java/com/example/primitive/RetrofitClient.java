package com.example.primitive;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String BASE_URL = "http://172.20.10.4:8080";
    private static ApiService retrofitAPI;
    private static RetrofitClient instance = null;

    private RetrofitClient(){

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI =retrofit.create(ApiService.class);
    }

    public static RetrofitClient getInstance(){
        if(instance==null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static ApiService getRetrofitInterface(){
        return retrofitAPI;
    }

}


