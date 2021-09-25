package com.zw.retrofit_demo.retrofit.converter3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MyConverterFactory3 extends Converter.Factory {
    private static MyResponseBodyConverter3 myResponseBodyConverter3 = new MyResponseBodyConverter3();
    private static MyRequestBodyConverter3 myRequestBodyConverter3 = new MyRequestBodyConverter3();

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return myResponseBodyConverter3;
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    // 参数要使用@Body这种形式 否则 request 方法会不起作用。
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        if (type == String.class) {
//            return myRequestBodyConverter3;
//        }
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}