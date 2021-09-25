package com.zw.retrofit_demo.retrofit.adapter3;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MyCallDemo3<R> {
    private final Call call;

    public MyCallDemo3(Call call) {
        this.call = call;
    }

    public R get() {
        try {
            Response<ResponseBody> responseBody = call.execute();
            return (R) responseBody.message();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}