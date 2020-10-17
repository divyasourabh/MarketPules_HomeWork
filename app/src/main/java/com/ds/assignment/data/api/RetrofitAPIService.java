package com.ds.assignment.data.api;

import com.ds.assignment.data.model.LoginModel;
import com.ds.assignment.data.model.MessageModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPIService {

    @POST("/api/login")
    Call<LoginModel> login(@Body Map<String, String> loginParams);

    @GET("/api/messages")
    Single<List<MessageModel>> getAllMessages();

    @POST("/api/messages")
    Single<MessageModel> postMessage(@Body Map<String, String> messageParams);
}
