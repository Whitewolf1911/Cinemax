package com.alibasoglu.cinemax.auth.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentIntroBinding
import com.alibasoglu.cinemax.utils.showTextToast
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IntroFragment : BaseFragment(R.layout.fragment_intro) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentIntroBinding::bind)

    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        handleFacebookLogin()
        handleIfHaveLoggedInUser()
    }

    private fun handleFacebookLogin() {

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() {}

                override fun onError(error: FacebookException) {}
            })

        binding.facebookSignupButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
        }
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
            googleSignupButton.setOnClickListener {
                val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.request_id_token))
                    .requestEmail()
                    .requestProfile()
                    .build()
                val signInClient = GoogleSignIn.getClient(requireContext(), options)
                signInClient.signInIntent.also {
                    startActivityForResult(it, REQUEST_CODE_SIGN_IN)
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthForFirebase(it)
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            context?.showTextToast("Login Success")
                            setStartDestinationToHome()
                            nav(IntroFragmentDirections.actionIntroFragmentToHomeFragment())
                        } else {
                            context?.showTextToast("Facebook login failed")
                        }
                    }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    context?.showTextToast(e.toString())
                }
            }
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        showProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).addOnSuccessListener {
                    hideProgressDialog()
                    context?.showTextToast("Login success")
                    setStartDestinationToHome()
                    nav(IntroFragmentDirections.actionIntroFragmentToHomeFragment())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    context?.showTextToast(e.toString())
                    hideProgressDialog()
                }
            }
        }
    }

    private fun handleIfHaveLoggedInUser() {
        if (auth.currentUser != null) {
            setStartDestinationToHome()
            nav(IntroFragmentDirections.actionIntroFragmentToHomeFragment())
        }
    }

    private fun navToSignupFragment() {
        nav(IntroFragmentDirections.actionIntroFragmentToSignupFragment())
    }

    private fun navToLoginFragment() {
        nav(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
    }

    companion object {
        const val REQUEST_CODE_SIGN_IN = 0
    }
}
