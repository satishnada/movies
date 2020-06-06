package com.movie.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.movie.R
import com.movie.base.BaseViewModel
import com.movie.data.local.entities.User
import com.movie.data.remote.requestes.RegistrationRequest
import com.movie.data.repositories.UserRepository
import com.movie.ui.auth.AuthActivity
import com.movie.ui.auth.login.LoginFragmentDirections
import com.movie.xutil.ApiException
import com.movie.xutil.NoInternetException
import com.movie.xutil.SingleLiveEvent
import com.movie.xutil.showLogoutAlertDialog

class AuthViewModel(
    private val repository: UserRepository
) : BaseViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null

    val onStarted = SingleLiveEvent<Unit>()
    val onSuccess = SingleLiveEvent<User>()
    val onFailure = SingleLiveEvent<String>()

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClick(view: View) {
        onStarted.value = Unit
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            onFailure.value = "Invalid email or password"
            return
        }

        mainCoroutineScope {
            try {
                repository.saveUser(User(1, 1, "satish.nada98@gmail.com", "Satish Nada"))
            } catch (e: ApiException) {
                onFailure.value = e.message
            } catch (e: NoInternetException) {
                onFailure.value = e.message
            }
        }

    }

    fun onLogin(view: View) {
        view.findNavController().popBackStack()
    }

    fun onSignup(view: View) {
        val direction = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
        view.findNavController().navigate(direction)
    }


    fun onSignupButtonClick(view: View) {
        onStarted.value = Unit

        if (name.isNullOrEmpty()) {
            onFailure.value = "Name is required"
            return
        }

        if (email.isNullOrEmpty()) {
            onFailure.value = "Email is required"
            return
        }

        if (password.isNullOrEmpty()) {
            onFailure.value = "Please enter a password"
            return
        }

        if (password != passwordconfirm) {
            onFailure.value = "Password did not match"
            return
        }

        mainCoroutineScope {
            try {
                val registrationRequest = RegistrationRequest(name!!, email!!, password!!)
                val authResponse = repository.userRegistration(registrationRequest)
                authResponse.data?.let {
                    onSuccess.value = it
                    repository.saveUser(it)
                    return@mainCoroutineScope
                }
                onFailure.value = authResponse.message
            } catch (e: ApiException) {
                onFailure.value = e.message
            } catch (e: NoInternetException) {
                onFailure.value = e.message
            }
        }

    }

    fun logout() {
        ioCoroutineScope {
            repository.logout()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("viewModel")
        fun NavigationView.navItemClick(authViewModel: AuthViewModel) {
            menu.findItem(R.id.logout).setOnMenuItemClickListener {
                context.showLogoutAlertDialog {
                    authViewModel.logout()
                    Intent(context, AuthActivity::class.java).also {
                        context.startActivity(it)
                        (context as Activity).finishAffinity()
                    }
                }
                false
            }
        }
    }
}