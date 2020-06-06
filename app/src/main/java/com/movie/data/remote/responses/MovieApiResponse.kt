package com.movie.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.movie.data.local.entities.MovieEntity
import java.io.Serializable

class MovieApiResponse(
    @SerializedName("page")
    val page: Int = 0, // 1
    @SerializedName("results")
    val movieEntities: List<MovieEntity> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0, // 500
    @SerializedName("total_results")
    val totalResults: Int = 0 // 10000
) : Serializable