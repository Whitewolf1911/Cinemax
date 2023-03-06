package com.alibasoglu.cinemax.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentOnboardingBinding
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding

class OnboardingFragment : BaseFragment(R.layout.fragment_onboarding) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentOnboardingBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        hideBottomNavbar()
        val viewPagerAdapter = OnboardingAdapter(childFragmentManager)
        with(binding) {
            viewPager.adapter = viewPagerAdapter
            dotsIndicator.attachTo(viewPager)
            viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            progressButton.setImageDrawable(resources.getDrawable(R.drawable.progress_button_1))
                        }
                        1 -> {
                            progressButton.setImageDrawable(resources.getDrawable(R.drawable.progress_button_2))
                        }
                        2 -> {
                            progressButton.setImageDrawable(resources.getDrawable(R.drawable.progress_button_3))
                        }
                    }
                }
                override fun onPageScrollStateChanged(state: Int) {}
            })
            progressButton.setOnClickListener {
                //TODO scroll to next one
            }
        }


    }

}