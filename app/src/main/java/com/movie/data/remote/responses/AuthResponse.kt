package com.movie.data.remote.responses

import com.movie.base.BaseResponse
import com.movie.data.local.entities.User

data class AuthResponse(
    override val data: User? = null
) : BaseResponse()