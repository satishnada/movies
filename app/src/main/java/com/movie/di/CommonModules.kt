package com.movie.di

import androidx.lifecycle.ViewModelProvider
import com.movie.BuildConfig
import com.movie.BuildConfig.BASE_URL
import com.movie.data.local.AppDatabase
import com.movie.data.preferences.PreferenceProvider
import com.movie.data.remote.*
import com.movie.viewmodel.factory.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val dbModule = Kodein.Module("dbModule") {

    bind() from singleton {
        AppDatabase(context = instance())
    }

}

val preferenceModule = Kodein.Module("preferenceModule") {

    bind() from singleton {
        PreferenceProvider(context = instance())
    }

}

val networkModule = Kodein.Module("networkModule") {

    bind() from singleton {
        createLoggingInterceptor()
    }

    bind() from singleton {
        createConnectionInterceptor(context = instance())
    }

    bind() from singleton {
        createTMDBRequestInterceptor()
    }

    bind() from singleton {
        createMyApiRequestInterceptor(instance())
    }

    bind(tag = "HTTPClient") from singleton {
        createHttpClient(
            httpLoggingInterceptor = instance(),
            networkConnectionInterceptor = instance(),
            myApiRequestInterceptor = instance()
        )
    }

    bind(tag = "TMDBHTTPClient") from singleton {
        createTMDBHttpClient(
            httpLoggingInterceptor = instance(),
            networkConnectionInterceptor = instance(),
            TMDBRequestInterceptor = instance()
        )
    }

    bind() from singleton {
        createWebService<MyApi>(
            okHttpClient = instance(tag = "HTTPClient"),
            baseUrl = BASE_URL
        )
    }

    bind() from singleton {
        createWebService<TMDBApi>(
            okHttpClient = instance(tag = "TMDBHTTPClient"),
            baseUrl = BuildConfig.TMDB_BASE_URL
        )
    }

}

val viewModelFactoryModule = Kodein.Module("viewModelFactoryModule") {
    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(kodein.direct)
    }
}