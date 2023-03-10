package com.alibasoglu.cinemax.moviedetail.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentTvShowDetailBinding
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail
import com.alibasoglu.cinemax.utils.ShareDialog
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TvShowDetailFragment : BaseFragment(R.layout.fragment_tv_show_detail) {
    private val toolbarConfiguration = ToolbarConfiguration(
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack,
        endIconResId = R.drawable.ic_wishlist,
        endIconClick = ::addShowToWishList
    )
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentTvShowDetailBinding::bind)

    private val viewModel by viewModels<TvShowDetailViewModel>()

    private val castCrewAdapter = CastCrewAdapter()

    private var shareDialog: ShareDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIWithObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTvShowDetails()
        viewModel.getShowCastCrew()
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
            viewModel.showDetailState.collectLatest { showDetailState ->
                showDetailState.tvShowDetail?.let { tvShowDetail ->
                    with(binding) {
                        customToolbar.setTitle(tvShowDetail.name)
                        shareButton.setOnClickListener {
                            showShareDialog()
                        }
                        playButton.setOnClickListener {
                            navToShowTrailerFragment(tvShowDetail = tvShowDetail)
                        }

                        castCrewRecyclerView.adapter = castCrewAdapter

                        val posterUrl =
                            ImagesConfigData.secure_base_url + ImagesConfigData.poster_sizes?.get(3) + tvShowDetail.poster_path
                        Glide.with(requireContext())
                            .load(posterUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop().run {
                                into(backgroundImageView)
                                into(posterImageView)
                            }
                        yearTextView.text = tvShowDetail.first_air_date
                        ratingTextView.text = tvShowDetail.vote_average.toString()
                        genreTextView.text = tvShowDetail.genres
                        runtimeTextView.text = getString(R.string.minutes, tvShowDetail.episode_run_time.toString())
                        storyLineTextView.text = tvShowDetail.overview
                    }
                }
                if (showDetailState.isLoading) {
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

    private fun addShowToWishList() {
        //TODO add to wishlist
    }

    private fun showShareDialog() {
        shareDialog = ShareDialog(requireActivity())
        shareDialog?.startDialog()
    }

    private fun navToShowTrailerFragment(tvShowDetail: TvShowDetail) {
        //TODO nav to trailer
    }
}
