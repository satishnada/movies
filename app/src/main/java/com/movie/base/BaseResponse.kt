package com.movie.base

import com.google.gson.annotations.SerializedName

abstract class BaseResponse {
    @SerializedName("error_code")
    val errorCode: Int? = null

    val error: Boolean? = null

    val message: String? = null

    abstract val data: Any?
}