package com.alibasoglu.cinemax.auth.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentIntroBinding
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding

class IntroFragment : BaseFragment(R.layout.fragment_intro) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentIntroBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        hideBottomNavbar()
        with(binding) {
            signupButton.setOnClickListener {
                navToSignupFragment()
            }
            loginTextView.setOnClickListener {
                navToLoginFragment()
            }
        }
    }

    private fun navToSignupFragment() {
        nav(IntroFragmentDirections.actionIntroFragmentToSignupFragment())
    }

    private fun navToLoginFragment() {
        nav(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
    }

}
