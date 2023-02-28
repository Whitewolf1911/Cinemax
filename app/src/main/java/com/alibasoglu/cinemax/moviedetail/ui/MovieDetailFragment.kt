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
import com.alibasoglu.cinemax.utils.ShareDialog
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.showTextToast
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

    private val castCrewAdapter = CastCrewAdapter()

    private var shareDialog: ShareDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIWithObservers()
    }

    private fun initUIWithObservers() {
        viewLifecycleOwner.observe {
            viewModel.movieDetailsState.collectLatest { movieDetailState ->
                movieDetailState.movieDetail?.let { movieDetail ->
                    getToolbar()?.setTitle(movieDetail.title)
                    with(binding) {
                        shareButton.setOnClickListener {
                            showShareDialog()
                        }

                        castCrewRecyclerView.adapter = castCrewAdapter

                        val posterUrl =
                            ImagesConfigData.secure_base_url + ImagesConfigData.poster_sizes?.get(3) + movieDetail.poster_path
                        Glide.with(requireContext())
                            .load(posterUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop().run {
                                into(backgroundImageView)
                                into(posterImageView)
                            }
                        yearTextView.text = movieDetail.release_date
                        ratingTextView.text = movieDetail.vote_average.toString()
                        genreTextView.text = movieDetail.genre
                        runtimeTextView.text = getString(R.string.minutes, movieDetail.runtime.toString())
                        storyLineTextView.text = movieDetail.overview
                    }
                }
                if (movieDetailState.isLoading) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        }
        viewLifecycleOwner.observe {
            viewModel.movieCastCrewList.collectLatest {
                castCrewAdapter.submitList(it)
            }
        }
    }

    private fun addMovieToWishlist() {
        viewModel.insertMovieToDatabase()
        context?.showTextToast("Movie added to wishlist!")
    }

    private fun showShareDialog() {
        shareDialog = activity?.let { ShareDialog(it) }
        shareDialog?.startDialog()
    }

}
