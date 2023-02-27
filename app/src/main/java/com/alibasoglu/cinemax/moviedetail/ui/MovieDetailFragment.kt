package com.alibasoglu.cinemax.moviedetail.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentMovieDetailBinding
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment(R.layout.fragment_movie_detail) {

    private val toolbarConfiguration = ToolbarConfiguration(
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack,
        endIconResId = R.drawable.ic_wishlist,
        endIconClick = ::addMovieToWishlist
    )
    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentMovieDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        getToolbar()?.setTitle("Spider-Man No Way Home bla bla lablasdasd")
        with(binding) {
            Glide.with(requireContext())
                .load("https://cdn.shopify.com/s/files/1/0037/8008/3782/products/IMG_7259.jpg?v=1640349274")
                .centerCrop().run {
                    into(posterImageView)
                    into(backgroundImageView)
                }

            yearTextView.text = "2021"
            ratingTextView.text = "4.5"
            genreTextView.text = "Action"
            runtimeTextView.text = "132 Minutes"
            storyLineTextView.text =
                "Lorem ipspsum dolor psider amet Lorem ipspsum dolor psider amet Lorem ipspsum dolor psider amet Lorem ipspsum dolor psider amet Lorem ipspsum dolor psider amet "
        }
    }

    private fun addMovieToWishlist() {
        //TODO add movie to wishlist
    }

}
