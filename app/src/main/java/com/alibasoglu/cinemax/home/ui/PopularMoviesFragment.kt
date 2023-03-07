package com.alibasoglu.cinemax.home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentPopularMoviesBinding
import com.alibasoglu.cinemax.ui.MovieBigCardItemAdapter
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment(R.layout.fragment_popular_movies) {

    private val toolbarConfiguration = ToolbarConfiguration(
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack,
        titleResId = R.string.most_popular_movie
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentPopularMoviesBinding::bind)

    private val viewModel by viewModels<PopularMoviesViewModel>()

    private val bigCardAdapterListener = MovieBigCardItemAdapter.MoviesBigCardAdapterListener {
        navToMovieDetailFragment(it.id)
    }

    private val bigCardItemAdapter = MovieBigCardItemAdapter(bigCardAdapterListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
    }

    private fun initUI() {
        hideBottomNavbar()
        binding.moviesRecyclerView.adapter = bigCardItemAdapter
    }

    private fun navToMovieDetailFragment(movieId: Int) {
        nav(PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment(movieId))
    }

    private fun initObserver() {
        viewLifecycleOwner.observe {
            viewModel.popularMoviesState.collectLatest {
                bigCardItemAdapter.submitData(it)
            }
        }
    }
}