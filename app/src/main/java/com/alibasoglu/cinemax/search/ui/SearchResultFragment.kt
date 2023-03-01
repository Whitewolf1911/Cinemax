package com.alibasoglu.cinemax.search.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentSearchResultBinding
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchResultFragment : BaseFragment(R.layout.fragment_search_result) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentSearchResultBinding::bind)

    private val viewModel by viewModels<SearchResultViewModel>()

    private val personItemAdapter = PersonItemAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }


    private fun initUI() {
        hideBottomNavbar()
        with(binding) {
            actorsRecyclerView.adapter = personItemAdapter
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.personListState.collectLatest {
                personItemAdapter.submitList(it.personItemList)
            }
        }
    }

}
