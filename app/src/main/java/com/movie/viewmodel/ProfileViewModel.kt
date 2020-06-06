package com.movie.viewmodel

import com.movie.base.BaseViewModel
import com.movie.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : BaseViewModel() {

    val user = repository.getUser()

}
