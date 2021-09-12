package com.zw.retrofit_demo.retrofit.converter2;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyStringConverterDemo1 implements Converter<ResponseBody, String> {
    public static final String TAG = MyStringConverterDemo1.class.getSimpleName() + "-xxx";

    @Override
    public String convert(ResponseBody value) throws IOException {
        Log.i(TAG, "MyStringConverterDemo1#convert ......");
        return value.string();
    }
}
