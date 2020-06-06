package com.movie.data.repositories

import com.movie.data.local.AppDatabase
import com.movie.data.local.entities.User
import com.movie.data.remote.MyApi
import com.movie.data.remote.SafeApiRequest
import com.movie.data.remote.requestes.LoginRequest
import com.movie.data.remote.requestes.RegistrationRequest
import com.movie.data.remote.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userLogin(loginRequest: LoginRequest): AuthResponse {
        return apiRequest { api.userLogin(loginRequest) }
    }

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ): AuthResponse {
        return apiRequest { api.userSignup(name, email, password) }
    }

    suspend fun userRegistration(
        registrationRequest: RegistrationRequest
    ): AuthResponse {
        return apiRequest { api.userRegistration(registrationRequest) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

    fun logout() {
        db.clearAllTables()
    }
}