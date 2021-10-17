package com.example.hack_it_out_app;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HealthCareApi {
    @POST("healthcare/user/verify/")
    Call<ResponseBody> verify(@Body TokenClass tokenClass);

    @POST("healthcare/user/login/")
    Call<String> login(@Body UserLoginData userLoginData);

    @GET("healthcare/user/exists")
    Call<String> checkEmail(@Header("email") String user_email);

    @POST("healthcare/user/egister/")
    Call<ResponseBody> newCustomer(@Body Customer customer);

    @GET("healthcare/service/")
    Call<ArrayList<Service>> getServices(@Header("Authorization") String authHeader);
}
