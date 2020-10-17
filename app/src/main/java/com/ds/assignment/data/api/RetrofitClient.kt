package com.ds.assignment.data.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    const val BASE_URL = "https://android-messaging-app-in-2020.herokuapp.com"

    public fun getRetrofit(context: Context?): Retrofit {
        return Retrofit.Builder()
            .client(provideOkHttp(context))
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttp(context: Context?): OkHttpClient? {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(context))
            .build()
    }
}