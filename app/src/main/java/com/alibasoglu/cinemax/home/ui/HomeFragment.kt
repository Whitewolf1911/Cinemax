package com.alibasoglu.cinemax.home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.GenresData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentHomeBinding
import com.alibasoglu.cinemax.ui.MoviesBasicCardAdapter
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val carouselAdapter = MoviesCarouselAdapter()

    private val moviesBasicCardAdapter = MoviesBasicCardAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        showBottomNavbar()
        with(binding) {
            carouselRecyclerView.apply {
                this.adapter = carouselAdapter
                set3DItem(true)
                setAlpha(true)
            }
            popularMoviesRecyclerView.adapter = moviesBasicCardAdapter

            seeAllTextView.setOnClickListener {
                //TODO nav to most popular fragment
            }

            wishlistButton.setOnClickListener {
                //TODO nav to wishlist fragment
            }

            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("All"))
            GenresData.genres.forEach { genre ->
                categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText(genre.name))
            }

        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.popularMoviesState.collectLatest {
                moviesBasicCardAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.observe {
            viewModel.upcomingMoviesState.collectLatest {
                carouselAdapter.submitList(it)
            }
        }
    }
}
