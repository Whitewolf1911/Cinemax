package com.alibasoglu.cinemax.search.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentRecommendedMoviesBinding
import com.alibasoglu.cinemax.ui.MovieBigCardItemAdapter
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RecommendedMoviesFragment : BaseFragment(R.layout.fragment_recommended_movies) {

    private val toolbarConfiguration = ToolbarConfiguration(
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack,
        titleResId = R.string.recommended
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentRecommendedMoviesBinding::bind)

    private val viewModel by viewModels<RecommendedMoviesViewModel>()

    private val bigCardAdapterListener = MovieBigCardItemAdapter.MoviesBigCardAdapterListener {
        navToMovieDetailFragment(it.id)
    }

    private val bigCardItemAdapter = MovieBigCardItemAdapter(bigCardAdapterListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        hideBottomNavbar()
        binding.moviesRecyclerView.adapter = bigCardItemAdapter
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.recommendedMoviesState.collectLatest {
                bigCardItemAdapter.submitData(it)
            }
        }

    }

    private fun navToMovieDetailFragment(movieId: Int) {
        nav(RecommendedMoviesFragmentDirections.actionRecommendedMoviesFragmentToMovieDetailFragment(movieId))
    }
}
