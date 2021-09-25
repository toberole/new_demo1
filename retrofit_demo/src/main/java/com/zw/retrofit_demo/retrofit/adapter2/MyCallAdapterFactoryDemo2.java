package com.zw.retrofit_demo.retrofit.adapter2;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

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
public class MyCallAdapterFactoryDemo2 extends CallAdapter.Factory {
    private static MyCallAdapterFactoryDemo2 myCallAdapterFactoryDemo1 = new MyCallAdapterFactoryDemo2();

    public static MyCallAdapterFactoryDemo2 create() {
        return myCallAdapterFactoryDemo1;
    }

    /*
         CallAdapter
            Adapts a Call with response type R into the type of T. Instances
            are created by a factory which is installed into the Retrofit instance.
     */
    @Nullable
    @Override
    // CallAdapter<R, T>
    public CallAdapter<Call, MyCallDemo2> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 获取原始类型
        Class<?> returnType_rawType = getRawType(returnType);
        // 返回值必须是CustomCall并且带有泛型
        if (returnType_rawType == MyCallDemo2.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new MyCallAdapterDemo2(callReturnType);
        }
        return null;
    }
}
