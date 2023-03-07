package com.alibasoglu.cinemax.profile.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentProfileBinding
import com.alibasoglu.cinemax.utils.showTextToast
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val toolbarConfiguration = ToolbarConfiguration(titleResId = R.string.profile)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private lateinit var auth: FirebaseAuth

    private var logoutDialog: LogoutDialog? = null

    private var currentUser: FirebaseUser? = null

    private val logOutRequestListener = LogoutDialog.LogOutRequestListener {
        auth.signOut()
        context?.showTextToast("Logged out")
        setStartDestinationToIntro()
        nav(ProfileFragmentDirections.actionProfileFragmentToIntroFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }


    private fun initUI() {
        showBottomNavbar()
        with(binding) {
            legalPoliciesButton.setOnClickListener {
                nav(ProfileFragmentDirections.actionProfileFragmentToPrivacyPolicyFragment())
            }
            editProfileButton.setOnClickListener {
                nav(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }
            logoutButton.setOnClickListener {
                showLogoutDialog()
            }
            if (!currentUser?.email.isNullOrEmpty()) {
                emailTextView.text = currentUser?.email
            } else {
                emailTextView.text = currentUser?.providerData?.get(1)?.email
            }
            nameTextView.text = currentUser?.displayName
            Glide.with(requireContext())
                .load(currentUser?.photoUrl)
                .centerCrop().placeholder(R.drawable.ic_person_24)
                .into(personImageView)
        }
    }

    private fun showLogoutDialog() {
        logoutDialog = LogoutDialog(requireActivity(), logOutRequestListener)

        logoutDialog?.startDialog()
    }
}