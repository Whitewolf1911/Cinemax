package com.alibasoglu.cinemax.profile.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentProfileBinding
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val toolbarConfiguration = ToolbarConfiguration(titleResId = R.string.profile)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private var logoutDialog: LogoutDialog? = null

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
        }
    }

    private fun showLogoutDialog() {
        logoutDialog = activity?.let { LogoutDialog(it) }
        logoutDialog?.startDialog()
    }
}