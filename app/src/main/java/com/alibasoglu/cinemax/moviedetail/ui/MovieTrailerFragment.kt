package com.alibasoglu.cinemax.moviedetail.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentMovieTrailerBinding
import com.alibasoglu.cinemax.utils.dateFormatter
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.pxFromDp
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("SourceLockedOrientationActivity")
@AndroidEntryPoint
class MovieTrailerFragment : BaseFragment(R.layout.fragment_movie_trailer) {
    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.trailer,
        startIconResId = R.drawable.ic_arrow_back,
        startIconClick = ::navBack,
        endIconResId = R.drawable.ic_wishlist
    )

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentMovieTrailerBinding::bind)

    private val viewModel by viewModels<MovieTrailerViewModel>()

    private var isFullscreen = false

    private var isInitialized = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        hideBottomNavbar()
        initUI()
        handleBackButton()
    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initUI() {
        with(binding) {
            with(viewModel.movieDetail) {
                synopsisTextView.text = overview
                movieNameTextView.text = title
                releaseDateTextView.text = dateFormatter(release_date)
                genreTextView.text = genre
            }
        }
    }

    private fun handleBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isFullscreen) {
                    exitFullScreen()
                } else {
                    navBack()
                }
            }
        })
    }

    private fun setupPlayer(trailerKey: String) {
        binding.youtubePlayer.enableAutomaticInitialization = false
        lifecycle.addObserver(binding.youtubePlayer)

        val options = IFramePlayerOptions.Builder().controls(0).build()

        val youTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {}

            override fun onReady(youTubePlayer: YouTubePlayer) {
                val defaultPlayerUiController = DefaultPlayerUiController(binding.youtubePlayer, youTubePlayer)
                defaultPlayerUiController.setFullScreenButtonClickListener {
                    if (isFullscreen) {
                        exitFullScreen()
                    } else {
                        enterFullScreen()
                    }
                }
                binding.youtubePlayer.setCustomPlayerUi(defaultPlayerUiController.rootView)
                youTubePlayer.loadVideo(trailerKey, 0F)
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {}
        }

        binding.youtubePlayer.initialize(youTubePlayerListener, options)

    }

    private fun hideSystemUI() {
        activity?.window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            WindowInsetsControllerCompat(it, binding.mainContainer).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    private fun showSystemUI() {
        activity?.window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, true)
            WindowInsetsControllerCompat(it, binding.mainContainer).show(WindowInsetsCompat.Type.systemBars())
        }
    }

    private fun setMargins(left: Float, top: Float, right: Float) {
        val params = binding.youtubePlayer.layoutParams as ViewGroup.MarginLayoutParams
        pxFromDp(requireContext(), left)
        params.setMargins(
            pxFromDp(requireContext(), left).toInt(),
            pxFromDp(requireContext(), top).toInt(),
            pxFromDp(requireContext(), right).toInt(),
            0
        )
        binding.youtubePlayer.layoutParams = params
    }

    private fun enterFullScreen() {
        binding.youtubePlayer.enterFullScreen()
        isFullscreen = true
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        hideSystemUI()
        hideToolbar()
        setMargins(0f, 0f, 0f)
    }

    private fun exitFullScreen() {
        binding.youtubePlayer.exitFullScreen()
        isFullscreen = false
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        showSystemUI()
        showToolbar()
        setMargins(24f, 28f, 24f)
    }


    private fun initObserver() {
        viewLifecycleOwner.observe {
            viewModel.trailerId.collectLatest {
                it?.let {
                    if (isInitialized.not()) {
                        setupPlayer(it)
                        isInitialized = true
                    }
                }
            }
        }
    }
}
