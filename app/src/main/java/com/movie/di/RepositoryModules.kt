package com.movie.di

import com.movie.data.repositories.MoviesListRepository
import com.movie.data.repositories.UserRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val repositoryModules = Kodein.Module("repositoryModules") {

    bind() from singleton {
        UserRepository(api = instance(), db = instance())
    }

    bind() from singleton {
        MoviesListRepository(tmdbApi = instance(), db = instance())
    }

}