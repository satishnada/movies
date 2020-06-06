package com.movie.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.movie.R
import com.movie.base.BaseActivity
import com.movie.ui.auth.AuthActivity
import com.movie.ui.home.HomeActivity
import com.movie.viewmodel.ProfileViewModel
import com.movie.xutil.RootCheckUtils
import com.movie.xutil.dLog
import com.movie.xutil.kodeinViewModel

class SplashActivity : BaseActivity() {

    private val viewModel: ProfileViewModel by kodeinViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!RootCheckUtils.checkRoot()) {
            viewModel.user.observe(this, Observer { user ->
                if (user != null) {
                    Intent(this, HomeActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                } else {
                    Intent(this, AuthActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
            })
        } else {
            dLog<SplashActivity>("Device rooted")
        }
    }
}
