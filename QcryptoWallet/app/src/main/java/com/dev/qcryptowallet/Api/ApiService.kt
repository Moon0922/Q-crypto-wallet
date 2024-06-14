package com.dev.qcryptowallet.Api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.Serializable

@Keep
data class Response(
    @SerializedName("res") val res: String
)

data class Phone (val phone: String)

data class User(val first_name: String,
                val last_name: String,
                val email: String,
                val phone: String,
                val zipcode: String,
                val country: String) : Serializable
data class CheckCode(val phone: String, val code: String)
interface ApiService {
    @POST("api/verify_phone")
    suspend fun phoneVerify(@Body phone: Phone): Response

    @POST("api/check_code")
    suspend fun checkCode(@Body checkCode: CheckCode): Response

    @POST("api/register")
    suspend fun register(@Body user: User): Response
}