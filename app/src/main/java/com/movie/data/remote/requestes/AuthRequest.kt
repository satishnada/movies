package com.movie.data.remote.requestes

data class RegistrationRequest(
    val user_name: String = "",
    val email: String = "",
    val password: String = "",
    val notification_token: String = ""
)

data class LoginRequest(
    val email: String = "",
    val password: String = "",
    val notification_token: String = ""
)