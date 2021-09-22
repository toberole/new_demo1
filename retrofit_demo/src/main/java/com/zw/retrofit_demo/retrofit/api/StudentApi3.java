package com.zw.retrofit_demo.retrofit.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentApi3 {
    @GET("/")
    Call<ResponseBody> test1();
} 