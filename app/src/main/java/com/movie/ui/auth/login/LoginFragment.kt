package com.movie.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.movie.base.BaseFragment
import com.movie.databinding.FragmentLoginBinding
import com.movie.ui.home.HomeActivity
import com.movie.viewmodel.AuthViewModel
import com.movie.xutil.*
import kotlinx.android.synthetic.main.fragment_login.*


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment() {

    private val viewModel: AuthViewModel by kodeinViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onStarted.observe(viewLifecycleOwner, Observer {
            hideKeyboard(root_layout)
            progress_bar.show()
        })
        viewModel.onFailure.observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            root_layout.showSnackbar(it)
        })
        viewModel.onSuccess.observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
        })

        viewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer { user ->
            //if (user != null) {
                Intent(context, HomeActivity::class.java).also {
                    startActivity(it)
                    activity?.finish()
                }
            //}
        })
    }
}
