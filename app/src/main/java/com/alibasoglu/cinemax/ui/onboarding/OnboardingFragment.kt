package com.alibasoglu.cinemax.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentOnboardingBinding
import com.alibasoglu.cinemax.utils.setImageDrawableWithAnimation
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment(R.layout.fragment_onboarding) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    private val viewModel by viewModels<OnboardingViewModel>()

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
                            progressButton.setImageDrawableWithAnimation(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.progress_button_1
                                ), 200
                            )
                        }
                        1 -> {
                            progressButton.setImageDrawableWithAnimation(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.progress_button_2
                                ), 200
                            )
                        }
                        2 -> {
                            progressButton.setImageDrawableWithAnimation(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.progress_button_3
                                ), 200
                            )
                        }
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
            progressButton.setOnClickListener {
                when (viewPager.currentItem) {
                    0 -> viewPager.currentItem = 1
                    1 -> viewPager.currentItem = 2
                    2 -> {
                        viewModel.setOnBoardingShown()
                        nav(OnboardingFragmentDirections.actionOnboardingFragmentToIntroFragment())
                    }
                }
            }
        }

    }

}