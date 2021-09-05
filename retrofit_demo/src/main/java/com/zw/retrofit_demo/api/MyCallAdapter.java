package com.zw.retrofit_demo.api;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
/*
public interface CallAdapter<T> {

  // 直正数据的类型 如Call<T> 中的 T
  // 这个 T 会作为Converter.Factory.responseBodyConverter 的第一个参数
  // 可以参照上面的自定义Converter
  Type responseType();

  <R> T adapt(Call<R> call);

  // 用于向Retrofit提供CallAdapter的工厂类
  abstract class Factory {
    // 在这个方法中判断是否是我们支持的类型，returnType 即Call<Requestbody>和`Observable<Requestbody>`
    // RxJavaCallAdapterFactory 就是判断returnType是不是Observable<?> 类型
    // 不支持时返回null
    public abstract CallAdapter<?> get(Type returnType, Annotation[] annotations,
    Retrofit retrofit);

    // 用于获取泛型的参数 如 Call<Requestbody> 中 Requestbody
    protected static Type getParameterUpperBound(int index, ParameterizedType type) {
      return Utils.getParameterUpperBound(index, type);
    }

    // 用于获取泛型的原始类型 如 Call<Requestbody> 中的 Call
    // 上面的get方法需要使用该方法。
    protected static Class<?> getRawType(Type type) {
      return Utils.getRawType(type);
    }
  }
}
 */
public class MyCallAdapter implements CallAdapter<Student, String> {
    @Override
    public Type responseType() {
        return null;
    }

    @Override
    public String adapt(Call<Student> call) {
        return null;
    }
}
