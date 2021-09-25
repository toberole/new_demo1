package com.zw.retrofit_demo.retrofit.converter3;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class MyRequestBodyConverter3 implements Converter<String, RequestBody> {
    @Override
    public RequestBody convert(String value) throws IOException {
        value = value + "_" + System.currentTimeMillis();
        RequestBody requestBody = RequestBody.create(MediaType.parse("mp3"), value.getBytes());
        return requestBody;
    }
}