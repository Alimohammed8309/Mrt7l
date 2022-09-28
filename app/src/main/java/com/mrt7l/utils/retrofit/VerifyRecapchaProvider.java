package com.mrt7l.utils.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyRecapchaProvider {

    private static Retrofit retrofit = null;
    private static Interceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder();

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }).connectTimeout(250, TimeUnit.SECONDS).readTimeout(250,TimeUnit.SECONDS)
            .addInterceptor(interceptor).build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.google.com/recaptcha/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}