package com.movie.data.repositories

import com.movie.data.local.AppDatabase
import com.movie.data.local.entities.MovieEntity
import com.movie.data.remote.SafeApiRequest
import com.movie.data.remote.TMDBApi
import com.movie.data.remote.responses.MovieApiResponse

class MoviesListRepository(private val tmdbApi: TMDBApi, private val db: AppDatabase) :
    SafeApiRequest() {

    suspend fun getMovies(
        type: String,
        page: Long
    ): MovieApiResponse {
        return tmdbApiRequest {
            tmdbApi.fetchMoviesByType(type, page)
        }
    }

    suspend fun saveMovies(movieEntity: List<MovieEntity>) =
        db.getMovieDao().upsert(movieEntity)

    suspend fun getMovies() = db.getMovieDao().getMovieByPopularity()
    suspend fun getMovies(ids: List<Long>) = db.getMovieDao().getMovieByIds(ids)

}