package com.alibasoglu.cinemax.moviedetail.ui.tvshow

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
import com.alibasoglu.cinemax.moviedetail.ui.CastCrewAdapter
import com.alibasoglu.cinemax.profile.settings.SeasonListItem
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

    private var seasonsDialog: SeasonDialog? = null

    var list = mutableListOf(
        SeasonListItem(seasonId = 1, seasonTitle = "Season 1", isSelected = true),
        SeasonListItem(seasonId = 2, seasonTitle = "Season 2", isSelected = false),
        SeasonListItem(seasonId = 3, seasonTitle = "Season 3", isSelected = false),
        SeasonListItem(seasonId = 4, seasonTitle = "Season 4", isSelected = false),
        SeasonListItem(seasonId = 5, seasonTitle = "Season 5", isSelected = false),
        SeasonListItem(seasonId = 6, seasonTitle = "Season 6", isSelected = false),
        SeasonListItem(seasonId = 7, seasonTitle = "Season 7", isSelected = false),
        SeasonListItem(seasonId = 8, seasonTitle = "Season 8", isSelected = false),
        SeasonListItem(seasonId = 9, seasonTitle = "Season 9", isSelected = false),
        SeasonListItem(seasonId = 10, seasonTitle = "Season 10", isSelected = false),
        SeasonListItem(seasonId = 11, seasonTitle = "Season 11", isSelected = false),
        SeasonListItem(seasonId = 12, seasonTitle = "Season 12", isSelected = false),
    )

    private val seasonDialogListener = SeasonDialog.OnDifferentSeasonSelect {
        list.forEach { item ->
            item.isSelected = false
        }
        list.find { s ->
            s.seasonId == it.seasonId
        }.also { selectedItem ->
            selectedItem?.isSelected = true
        }
        binding.seasonsButton.text = it.seasonTitle
    }

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
        binding.seasonsButton.text = list.firstOrNull()?.seasonTitle
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
        with(binding) {
            shareButton.setOnClickListener {
                showShareDialog()
            }
            seasonsButton.setOnClickListener {
                initSeasonsDialog()
            }
            castCrewRecyclerView.adapter = castCrewAdapter
        }

        viewLifecycleOwner.observe {
            viewModel.showDetailState.collectLatest { showDetailState ->
                showDetailState.tvShowDetail?.let { tvShowDetail ->
                    with(binding) {
                        customToolbar.setTitle(tvShowDetail.name)
                        val posterUrl =
                            ImagesConfigData.secure_base_url + ImagesConfigData.poster_sizes?.get(3) + tvShowDetail.poster_path
                        Glide.with(requireContext())
                            .load(posterUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop().run {
                                into(backgroundImageView)
                                into(posterImageView)
                            }
                        playButton.setOnClickListener {
                            navToShowTrailerFragment(tvShowDetail = tvShowDetail)
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

    private fun initSeasonsDialog() {
        seasonsDialog = SeasonDialog(activity = requireActivity(), listener = seasonDialogListener, seasonList = list)
        seasonsDialog?.startDialog()
    }

    private fun navToShowTrailerFragment(tvShowDetail: TvShowDetail) {
        //TODO nav to trailer
    }
}
