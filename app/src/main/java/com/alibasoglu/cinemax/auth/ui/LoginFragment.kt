package com.alibasoglu.cinemax.auth.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentLoginBinding
import com.alibasoglu.cinemax.utils.Resource
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.login,
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        with(binding) {
            forgotPasswordTextView.setOnClickListener {
                navToForgotPasswordFragment()
            }
            loginButton.setOnClickListener {
                loginUser(email = emailEditText.text.toString(), password = passwordEditText.text.toString())
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModel.loginWithEmailPassword(email, password)
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.loginState.collectLatest { state ->
                when (state) {
                    is Resource.Success -> {
                        hideProgressDialog()
                        Toast.makeText(requireContext(), "Login success", Toast.LENGTH_SHORT).show()
                        setStartDestinationToHome()
                        navToHomeFragment()
                    }
                    is Resource.Error -> {
                        hideProgressDialog()
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        if (state.isLoading)
                            showProgressDialog()
                    }
                }

            }
        }
    }

    private fun navToForgotPasswordFragment() {
        //TODO nav to forgotPassword fragment
    }

    private fun navToHomeFragment() {
        nav(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }
}
