package com.zw.retrofit_demo.retrofit.adapter.converter;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MyStringConverterFactory extends Converter.Factory {

    public static final MyStringConverterFactory INSTANCE = new MyStringConverterFactory();

    public static MyStringConverterFactory create() {
        return INSTANCE;
    }

    // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return MyStringConverter.INSTANCE;
        }
        //其它类型我们不处理，返回null就行
        return null;
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(
            Type type,
            Annotation[] parameterAnnotations,
            Annotation[] methodAnnotations,
            Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
