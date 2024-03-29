package com.alibasoglu.cinemax.search.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.alibasoglu.cinemax.GenresData
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.data.remote.pagingsource.MoviesPagingSource
import com.alibasoglu.cinemax.databinding.FragmentSearchBinding
import com.alibasoglu.cinemax.ui.MoviesBasicCardAdapter
import com.alibasoglu.cinemax.utils.QUERY_SEARCH_DELAY
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchFragmentViewModel>()

    private var searchJob: Job? = null

    private val moviesBasicCardAdapterListener =
        MoviesBasicCardAdapter.MoviesCardAdapterListener { movieBasicCardItem ->
            navToMovieDetailFragment(movieBasicCardItem.id)
        }
    private val moviesBasicCardAdapter = MoviesBasicCardAdapter(moviesBasicCardAdapterListener)

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
            GenresData.genres.forEach { genre ->
                categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText(genre.name))
            }

            val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val allGenres = GenresData.genres.find {
                        it.id == 0
                    }?.name.orEmpty()
                    val selectedTabGenreName = GenresData.genres.find {
                        it.name == tab?.text
                    }?.name
                    viewModel.filterRecommendedMovies(selectedTabGenreName ?: allGenres)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            }
            categoriesTabLayout.addOnTabSelectedListener(onTabSelectedListener)

            recommendedMoviesRecyclerView.adapter = moviesBasicCardAdapter

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
            seeAllTextView.setOnClickListener {
                navToRecommendedMoviesFragment()
            }

        }
    }

    private fun navToMovieDetailFragment(movieId: Int) {
        nav(SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(movieId))
    }

    private fun navToSearchResultFragment(searchQuery: String) {
        nav(SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(searchQuery))
    }

    private fun navToRecommendedMoviesFragment() {
        nav(SearchFragmentDirections.actionSearchFragmentToRecommendedMoviesFragment())
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.recommendedMoviesState.collectLatest { list ->
                moviesBasicCardAdapter.submitData(PagingData.from(list))
            }
        }
        viewLifecycleOwner.observe {
            viewModel.movieOfTheDayState.collectLatest { movieItem ->
                val typeText = when (movieItem.mediaType) {
                    MoviesPagingSource.TYPE_TV -> getString(R.string.tv)
                    MoviesPagingSource.TYPE_MOVIE -> getString(R.string.movie)
                    else -> getString(R.string.movie)
                }
                val imageUrl =
                    ImagesConfigData.secure_base_url + ImagesConfigData.poster_sizes?.get(1) + movieItem.poster_path
                binding.todayMovieItem.apply {
                    genreTextView.text = movieItem.genre
                    nameTextView.text = movieItem.title
                    yearTextView.text = movieItem.release_date
                    ratingTextView.text = movieItem.vote_average.toString()
                    mediaTypeTextView.text = typeText
                    Glide
                        .with(requireContext())
                        .load(imageUrl)
                        .centerCrop()
                        .into(posterImageView)
                    root.setOnClickListener {
                        navToMovieDetailFragment(movieItem.id)
                    }
                }
            }
        }
    }
}
