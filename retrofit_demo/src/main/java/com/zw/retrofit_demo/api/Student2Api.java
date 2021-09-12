package com.zw.retrofit_demo.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface Student2Api {
    @GET("/")
    Call<ResponseBody> test1(
            @Field("id") long id,
            @Field("name") String name,
            @Field("age") int age
    );
}
