package com.alibasoglu.cinemax.auth.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentLoginBinding
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

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
                loginUser(email = emailEditText.text.toString(), password = passwordEditText.text.toString())
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(requireContext(), "Login success", Toast.LENGTH_SHORT).show()
                navToHomeFragment()
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_SHORT).show()
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
