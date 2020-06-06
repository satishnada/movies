package com.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import com.movie.base.BaseViewModel
import com.movie.data.local.entities.MovieEntity
import com.movie.data.remote.responses.MovieApiResponse
import com.movie.data.repositories.MoviesListRepository
import com.movie.xutil.ApiException
import com.movie.xutil.NoInternetException

class MoviesViewModel(private val repository: MoviesListRepository) : BaseViewModel() {

    val result = MutableLiveData<MovieApiResponse>()
    val error = MutableLiveData<String>()
    private var ids: List<Long> = listOf()
    val movies = MutableLiveData<List<MovieEntity>>()

    fun getMovies(type: String, page: Long) {
        mainCoroutineScope {
            try {
                result.value = repository.getMovies(type, page)
                ids = repository.saveMovies(result.value?.movieEntities!!)
                movies.value = repository.getMovies(ids)
            } catch (e: ApiException) {
                movies.value = repository.getMovies(ids)
                error.value = e.message
            } catch (e: NoInternetException) {
                movies.value = repository.getMovies(ids)
                error.value = e.message
            }
        }
    }
}