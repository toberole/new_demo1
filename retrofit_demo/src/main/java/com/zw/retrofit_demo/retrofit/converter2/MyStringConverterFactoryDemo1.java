package com.zw.retrofit_demo.retrofit.converter2;

import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
/*
public interface Converter<F, T> {
  // 实现从 F(rom) 到 T(o)的转换
  T convert(F value) throws IOException;

  // 用于向Retrofit提供相应Converter的工厂
  abstract class Factory {
    // 这里创建从ResponseBody其它类型的Converter，如果不能处理返回null
    // 主要用于对响应体的处理
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
    Retrofit retrofit) {
      return null;
    }

    // 在这里创建 从自定类型到ResponseBody 的Converter,不能处理就返回null，
    // 主要用于对Part、PartMap、Body注解的处理
    public Converter<?, RequestBody> requestBodyConverter(Type type,
    Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
      return null;
    }

    // 这里用于对Field、FieldMap、Header、Path、Query、QueryMap注解的处理
    // Retrfofit对于上面的几个注解默认使用的是调用toString方法
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
    Retrofit retrofit) {
      return null;
    }
  }
}
 */

/**
 * 从Call<ResponseBody> 转换为 Call<String>
 * 那么对应的F和T则分别对应ResponseBody和String
 */
public class MyStringConverterFactoryDemo1 extends Converter.Factory {
    public static final String TAG = MyStringConverterFactoryDemo1.class.getSimpleName() + "-xxx";

    private MyStringConverterDemo1 myStringConverterDemo1 = new MyStringConverterDemo1();
    private static MyStringConverterFactoryDemo1 myStringConverterFactoryDemo1 = new MyStringConverterFactoryDemo1();

    // 模仿GsonConverterFactory 提供一个create
    public static MyStringConverterFactoryDemo1 create() {
        return myStringConverterFactoryDemo1;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Log.i(TAG, "MyStringConverterFactoryDemo1#responseBodyConverter ......");

        // 实现从ResponseBody 到 String 的转换
        if (type == String.class) {
            return myStringConverterDemo1;
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Log.i(TAG, "###### MyStringConverterFactoryDemo1#requestBodyConverter ......");
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Log.i(TAG, "@@@@@@ MyStringConverterFactoryDemo1#stringConverter ......" + type.getTypeName());
        return super.stringConverter(type, annotations, retrofit);
    }
}
