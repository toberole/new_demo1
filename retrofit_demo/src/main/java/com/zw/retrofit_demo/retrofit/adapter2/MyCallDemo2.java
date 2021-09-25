package com.zw.retrofit_demo.retrofit.adapter2;

import java.io.IOException;

import retrofit2.Call;

public class MyCallDemo2<T> {
    private final Call<T> call;

    public MyCallDemo2(Call<T> call) {
        this.call = call;
    }

    //
    public String get() throws IOException {
        return call.execute().body().toString();
    }
}
