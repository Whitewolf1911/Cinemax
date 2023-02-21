package com.alibasoglu.cinemax

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val fragmentConfiguration = FragmentConfiguration()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
