package com.alibasoglu.cinemax.auth.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration

class SignupFragment : BaseFragment(R.layout.fragment_signup) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.sign_up,
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        hideBottomNavbar()
    }

}
