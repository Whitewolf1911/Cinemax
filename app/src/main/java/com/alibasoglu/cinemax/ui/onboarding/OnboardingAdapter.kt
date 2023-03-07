package com.alibasoglu.cinemax.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class OnboardingAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val fragmentList = listOf(
        FirstOnboardFragment(),
        SecondOnboardFragment(),
        ThirdOnboardFragment(),
    )

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

}