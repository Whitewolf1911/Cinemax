package com.alibasoglu.cinemax.moviedetail.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentMovieDetailBinding
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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

    private val viewModel by viewModels<MovieDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewLifecycleOwner.observe {
            viewModel.movieDetailsState.collectLatest { movieDetail ->
                getToolbar()?.setTitle(movieDetail.title)
                with(binding) {
                    val posterUrl =
                        ImagesConfigData.secure_base_url + ImagesConfigData.poster_sizes?.get(3) + movieDetail.poster_path
                    Glide.with(requireContext())
                        .load(posterUrl)
                        .centerCrop().run {
                            into(posterImageView)
                            into(backgroundImageView)
                        }
                    yearTextView.text = movieDetail.release_date
                    ratingTextView.text = movieDetail.vote_average.toString()
                    genreTextView.text = movieDetail.genre
                    runtimeTextView.text = "${movieDetail.runtime} Minutes"
                    storyLineTextView.text = movieDetail.overview

                }
            }
        }
    }

    private fun addMovieToWishlist() {
        //TODO add movie to wishlist
    }

}
