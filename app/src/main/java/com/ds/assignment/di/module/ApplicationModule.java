package com.ds.assignment.di.module;

import android.content.Context;

import com.ds.assignment.data.api.NetworkInterceptor;
import com.ds.assignment.data.api.RetrofitAPIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.ds.assignment.data.api.RetrofitBuilder.BASE_URL;

@Module
@InstallIn(ApplicationComponent.class) //This means that the dependencies provided here will be used across the application
public class ApplicationModule {

    @Provides
    String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@ApplicationContext Context context) {
        return new Retrofit.Builder()
                .client(provideOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    RetrofitAPIService provideRetrofitAPIService (Retrofit retrofit) {
        return retrofit.create(RetrofitAPIService.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient (Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor(context))
                .build();
    }
}

