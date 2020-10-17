package com.ds.assignment.data.api

import com.ds.assignment.data.model.LoginModel
import com.ds.assignment.data.model.MessageModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/api/login")
    suspend fun login(@Body LoginParams : Map<String, String>): LoginModel

    //    @POST("/api/login")
    //    Single<LoginModel> login(@Body Map<String, String> loginParams);
    @GET("/api/messages")
    suspend fun getAllMessages(): List<MessageModel>

    @POST("/api/messages")
    suspend fun postMessage(@Body messageParams: Map<String, String>): MessageModel
}