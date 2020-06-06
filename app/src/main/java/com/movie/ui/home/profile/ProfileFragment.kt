package com.movie.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.movie.base.BaseFragment
import com.movie.databinding.FragmentProfileBinding
import com.movie.viewmodel.ProfileViewModel
import com.movie.xutil.kodeinViewModel

class ProfileFragment : BaseFragment() {

    private val viewModel: ProfileViewModel by kodeinViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}
