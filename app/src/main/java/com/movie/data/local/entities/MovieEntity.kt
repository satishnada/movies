package com.movie.data.local.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.movie.ui.home.movies.MoviesAdapter.Companion.MOVIE_ITEM
import java.io.Serializable

@Entity
data class MovieEntity(
    @PrimaryKey
    var id: Int? = null,
    var adult: Boolean? = null,
    var backdrop_path: String? = null,
    @Ignore
    var genre_ids: List<Int>? = listOf(),
    var original_language: String? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    var title: String? = null,
    var video: Boolean? = null,
    var vote_average: Double? = null,
    var vote_count: Int? = null,

    @Ignore
    var loadMore: Boolean = false,
    @Ignore
    var viewType: Int? = MOVIE_ITEM
) : Serializable