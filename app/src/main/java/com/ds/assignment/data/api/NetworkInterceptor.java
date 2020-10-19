package com.ds.assignment.data.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ds.assignment.utils.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {

    private Context mContext;

    public NetworkInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = SharedPreferencesUtils.getToken(mContext);
        if (TextUtils.isEmpty(token)) {
            Log.d("Test1234","Empty Token");
        } else {
            Log.d("Test1234","Token = " + token);
        }

        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Branch-Auth-Token", token)
                .build();

        Response response = chain.proceed(request);
        return response;
    }
}
