package com.movie.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movie.data.local.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movieEntity: List<MovieEntity>): List<Long>

    @Query("SELECT * FROM MovieEntity ORDER BY popularity DESC")
    suspend fun getMovieByPopularity(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE id IN (:ids) ORDER BY popularity DESC")
    suspend fun getMovieByIds(ids: List<Long>): List<MovieEntity>
}