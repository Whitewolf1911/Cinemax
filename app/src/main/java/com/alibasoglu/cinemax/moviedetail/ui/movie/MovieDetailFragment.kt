package com.alibasoglu.cinemax.moviedetail.ui.movie

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentMovieDetailBinding
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.ui.CastCrewAdapter
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
        endIconClick = ::addOrRemoveMovieToWishlist
    )
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentMovieDetailBinding::bind)

    private val viewModel by viewModels<MovieDetailViewModel>()

    private val castCrewAdapter = CastCrewAdapter()

    private var isWishListed = false

    private var shareDialog: ShareDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIWithObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovieDetails()
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    private fun initUIWithObservers() {
        activity?.window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        binding.showMoreButton.setOnClickListener {
            binding.storyLineTextView.maxLines = Int.MAX_VALUE
            it.visibility = View.GONE
        }
        binding.customToolbar.configure(toolbarConfiguration)
        hideBottomNavbar()
        viewLifecycleOwner.observe {
            viewModel.movieDetailsState.collectLatest { movieDetailState ->
                movieDetailState.movieDetail?.let { movieDetail ->
                    with(binding) {
                        customToolbar.setTitle(movieDetail.title)
                        shareButton.setOnClickListener {
                            showShareDialog()
                        }
                        playButton.setOnClickListener {
                            navToMovieTrailerFragment(movieDetail = movieDetail)
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

        viewLifecycleOwner.observe {
            viewModel.wishListedState.collectLatest {
                isWishListed = it
            }
        }
    }

    private fun addOrRemoveMovieToWishlist() {
        if (isWishListed.not()) {
            viewModel.insertMovieToDatabase()
            context?.showTextToast(getString(R.string.added_to_wishlist))
        } else {
            viewModel.removeMovieFromDatabase()
            context?.showTextToast(getString(R.string.removed_from_wishlist))
        }
    }

    private fun showShareDialog() {
        shareDialog = activity?.let { ShareDialog(it) }
        shareDialog?.startDialog()
    }

    private fun navToMovieTrailerFragment(movieDetail: MovieDetail) {
        nav(
            MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieTrailerFragment(
                movieDetail = movieDetail,
                isMovie = true
            )
        )
    }

}
