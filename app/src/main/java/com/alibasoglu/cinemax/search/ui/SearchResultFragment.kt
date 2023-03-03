package com.alibasoglu.cinemax.search.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentSearchResultBinding
import com.alibasoglu.cinemax.home.ui.HomeFragment
import com.alibasoglu.cinemax.ui.MovieBigCardItemAdapter
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : BaseFragment(R.layout.fragment_search_result) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentSearchResultBinding::bind)

    private val viewModel by viewModels<SearchResultViewModel>()

    private val personItemAdapter = PersonItemAdapter()

    private var searchJob: Job? = null

    private val movieBigCardAdapterListener = MovieBigCardItemAdapter.MoviesBigCardAdapterListener { movieBigCardItem ->
        nav(SearchResultFragmentDirections.actionSearchResultFragmentToMovieDetailFragment(movieBigCardItem.id))
    }

    private val movieBigCardItemAdapter = MovieBigCardItemAdapter(movieBigCardAdapterListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        searchJob?.cancel()
    }


    private fun initUI() {
        hideBottomNavbar()
        with(binding) {
            actorsRecyclerView.adapter = personItemAdapter
            moviesRecyclerView.adapter = movieBigCardItemAdapter

            searchEditText.apply {
                doAfterTextChanged { searchQuery ->
                    searchQuery?.let {
                        searchJob?.cancel()
                        searchJob = lifecycleScope.launch {
                            delay(HomeFragment.QUERY_SEARCH_DELAY)
                            if (searchQuery.trim().length > 1) {
                                viewModel.searchPerson(searchQuery.toString())
                                viewModel.searchMovie(searchQuery.toString())
                            }
                        }
                    }
                }
                //For submit button
                setOnEditorActionListener { textView, _, _ ->
                    if (textView.text.trim().length > 1) {
                        viewModel.searchPerson(textView.text.toString())
                        viewModel.searchMovie(textView.text.toString())
                    }
                    return@setOnEditorActionListener true
                }
            }

            lifecycleScope.launch {
                movieBigCardItemAdapter.loadStateFlow.collectLatest { loadState ->
                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && movieBigCardItemAdapter.itemCount == 0

                    checkEmptyMovieList(isListEmpty)
                }
            }
        }
    }

    //TODO REFACTOR Here using state model etc.
    private fun initObservers() {
        with(binding) {
            viewLifecycleOwner.observe {
                viewModel.personListState.collectLatest {
                    personItemAdapter.submitList(it.personItemList)
                    if (it.personItemList.isEmpty()) {
                        actorsRecyclerView.visibility = View.GONE
                        actorsTextView.visibility = View.GONE
                    } else {
                        actorsRecyclerView.visibility = View.VISIBLE
                        actorsTextView.visibility = View.VISIBLE
                    }
                }
            }
            viewLifecycleOwner.observe {
                viewModel.moviesSearchState.collectLatest {
                    movieBigCardItemAdapter.submitData(it)
                }
            }
        }
    }

    private fun checkEmptyMovieList(isListEmpty: Boolean) {
        with(binding) {
            if (isListEmpty) {
                noResultBigTextView.visibility = View.VISIBLE
                noResultSmallTextView.visibility = View.VISIBLE
                noResultImageView.visibility = View.VISIBLE
                moviesTextView.visibility = View.GONE
            } else {
                noResultBigTextView.visibility = View.GONE
                noResultSmallTextView.visibility = View.GONE
                noResultImageView.visibility = View.GONE
                moviesTextView.visibility = View.VISIBLE
            }
        }
    }
}
