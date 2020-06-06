package com.movie.data.remote

import com.movie.data.remote.responses.MovieApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/{type}?language=en-US&region=US")
    suspend fun fetchMoviesByType(
        @Path("type") type: String,
        @Query("page") page: Long
    ): Response<MovieApiResponse>
}