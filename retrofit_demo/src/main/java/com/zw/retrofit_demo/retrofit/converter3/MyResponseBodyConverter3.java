package com.zw.retrofit_demo.retrofit.converter3;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyResponseBodyConverter3 implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        String s = value.string() + "_" + System.currentTimeMillis();
        return s;
    }
}