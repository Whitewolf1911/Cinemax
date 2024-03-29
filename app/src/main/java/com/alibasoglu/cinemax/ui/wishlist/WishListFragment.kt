package com.alibasoglu.cinemax.ui.wishlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentWishlistBinding
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WishListFragment : BaseFragment(R.layout.fragment_wishlist) {

    private val toolbarConfiguration = ToolbarConfiguration(titleResId = R.string.wishlist)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentWishlistBinding::bind)

    private val viewModel by viewModels<WishListViewModel>()

    private val wishListCardAdapterListener =
        object : WishListCardAdapter.WishListCardAdapterListener {
            override fun onClick(wishListCardItem: WishListCardItem) {
                if (wishListCardItem.isMovie) {
                    nav(WishListFragmentDirections.actionWishListFragmentToMovieDetailFragment(wishListCardItem.id))
                } else {
                    nav(WishListFragmentDirections.actionWishListFragmentToTvShowDetailFragment(wishListCardItem.id))
                }
            }

            override fun onLongClick(wishListCardItem: WishListCardItem) {
                //TODO handle long click
            }
        }

    private val wishListAdapter = WishListCardAdapter(wishListCardAdapterListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWishListedMovies()
    }

    private fun initUI() {
        showBottomNavbar()
        binding.wishlistRecyclerView.adapter = wishListAdapter
        viewLifecycleOwner.observe {
            viewModel.wishListedMoviesState.collectLatest {
                wishListAdapter.submitList(it)
                checkEmptyStatus(it.isEmpty())
            }
        }
    }

    private fun checkEmptyStatus(isEmpty: Boolean) {
        with(binding) {
            if (isEmpty) {
                noMovieImageView.visibility = View.VISIBLE
                subtitleTextView.visibility = View.VISIBLE
                noMovieTitleTextView.visibility = View.VISIBLE
            } else {
                noMovieImageView.visibility = View.GONE
                subtitleTextView.visibility = View.GONE
                noMovieTitleTextView.visibility = View.GONE
            }
        }
    }
}
