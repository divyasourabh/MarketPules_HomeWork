package com.ds.assignment.data.api;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    public static final String BASE_URL = "https://android-messaging-app-in-2020.herokuapp.com";

    public static Retrofit getRetrofitInstance (Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(provideOkHttp(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit;
    }

    private static OkHttpClient provideOkHttp (Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor(context))
                .build();
    }
}
