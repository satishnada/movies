package com.movie.data.remote

import com.movie.data.remote.requestes.LoginRequest
import com.movie.data.remote.requestes.RegistrationRequest
import com.movie.data.remote.responses.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @POST("userLogin.php")
    suspend fun userLogin(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>

    @POST("userRegistration.php")
    suspend fun userRegistration(
        @Body registrationRequest: RegistrationRequest
    ): Response<AuthResponse>
}

