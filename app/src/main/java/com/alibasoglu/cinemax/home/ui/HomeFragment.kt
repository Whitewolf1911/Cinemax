package com.alibasoglu.cinemax.home.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibasoglu.cinemax.GenresData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentHomeBinding
import com.alibasoglu.cinemax.ui.MoviesBasicCardAdapter
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val moviesBasicCardAdapterListener =
        MoviesBasicCardAdapter.MoviesCardAdapterListener { movieBasicCardItem ->
            navToMovieDetailFragment(movieBasicCardItem.id)
        }

    private val carouselAdapter = MoviesCarouselAdapter()

    private val moviesBasicCardAdapter = MoviesBasicCardAdapter(moviesBasicCardAdapterListener)

    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        binding.searchEditText.text?.clear()
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

            searchEditText.apply {
                doAfterTextChanged { searchQuery ->
                    searchQuery?.let {
                        searchJob?.cancel()
                        searchJob = lifecycleScope.launch {
                            delay(QUERY_SEARCH_DELAY)
                            if (searchQuery.trim().length > 1)
                                navToSearchResultFragment(searchQuery.toString())
                        }
                    }
                }
                //For submit button
                setOnEditorActionListener { textView, _, _ ->
                    if (textView.text.trim().length > 1) {
                        navToSearchResultFragment(textView.text.toString())
                    }
                    return@setOnEditorActionListener true
                }
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

    private fun navToMovieDetailFragment(movieId: Int) {
        nav(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movieId))
    }

    private fun navToSearchResultFragment(searchQuery: String) {
        nav(HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(searchQuery))
    }

    companion object {
        const val QUERY_SEARCH_DELAY = 1800L
    }

}
