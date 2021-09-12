package com.zw.retrofit_demo.retrofit.adapter2;

import java.io.IOException;

import retrofit2.Call;

public class MyCallDemo1<T> {
    private final Call<T> call;

    public MyCallDemo1(Call<T> call) {
        this.call = call;
    }

    //
    public String get() throws IOException {
        return call.execute().body().toString();
    }
}
