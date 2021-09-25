package com.zw.retrofit_demo.retrofit.adapter3;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class MyCallDemo3 {
    private Call call;

    public MyCallDemo3(Call<Call> call) {
        this.call = call;
    }

    public String get() {
        try {
            Response<String> response = call.execute();
            return response.message();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}