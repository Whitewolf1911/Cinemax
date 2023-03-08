package com.alibasoglu.cinemax.profile.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentLanguageBinding
import com.alibasoglu.cinemax.utils.setAppLocale
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment(R.layout.fragment_language) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.language,
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack
    )

    private val binding by viewBinding(FragmentLanguageBinding::bind)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enButton.setOnClickListener {
            context?.setAppLocale("en")
        }
        binding.trButton.setOnClickListener {
            context?.setAppLocale("tr")
        }
    }
}
