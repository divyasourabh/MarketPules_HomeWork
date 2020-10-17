package com.ds.assignment.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginModel(

    @SerializedName("auth_token")
    @Json(name = "auth_token")
    val authToken: String? = null,

    @SerializedName("error")
    @Json(name = "error")
    val error : String? = null
)

@JsonClass(generateAdapter = true)
data class LoginParams(
    @Json(name = "username") val userName: String? = null,
    @Json(name = "password") val password: String? = null
)

@JsonClass(generateAdapter = true)
data class MessageModel(
    @SerializedName("id")
    @Json(name = "id") val id: Int,
    @SerializedName("thread_id")
    @Json(name = "thread_id") val threadId: Int,
    @SerializedName("user_id")
    @Json(name = "user_id") val userId: String? = null,
    @SerializedName("agent_id")
    @Json(name = "agent_id") val agentId: String? = null,
    @SerializedName("body")
    @Json(name = "body") val body: String? = null,
    @SerializedName("timestamp")
    @Json(name = "timestamp") val timestamp: String? = null
)