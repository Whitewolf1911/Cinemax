package com.alibasoglu.cinemax.auth.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentLoginBinding
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.login,
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            forgotPasswordTextView.setOnClickListener {
                navToForgotPasswordFragment()
            }
            loginButton.setOnClickListener {
                //TODO handle login
            }
        }
    }

    private fun navToForgotPasswordFragment() {
        //TODO nav to forgotPassword fragment
    }
}
