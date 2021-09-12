package com.zw.retrofit_demo.retrofit.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
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
public class MyStringConverter implements Converter<ResponseBody, String> {
    public static final MyStringConverter INSTANCE = new MyStringConverter();

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
