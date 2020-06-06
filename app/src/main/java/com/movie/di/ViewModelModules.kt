package com.movie.di

import com.movie.viewmodel.AuthViewModel
import com.movie.viewmodel.MoviesViewModel
import com.movie.viewmodel.ProfileViewModel
import com.movie.viewmodel.SplashViewModel
import com.movie.xutil.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModules = Kodein.Module("viewModelModules") {

    bindViewModel<AuthViewModel>() with provider {
        AuthViewModel(repository = instance())
    }

    bindViewModel<ProfileViewModel>() with provider {
        ProfileViewModel(repository = instance())
    }

    bindViewModel<MoviesViewModel>() with provider {
        MoviesViewModel(repository = instance())
    }

    bindViewModel<SplashViewModel>() with provider {
        SplashViewModel(repository = instance())
    }

}