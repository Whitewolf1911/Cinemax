package com.alibasoglu.cinemax.moviedetail.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.FragmentMovieTrailerBinding
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        hideBottomNavbar()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
                    binding.youtubePlayer.toggleFullScreen()
                    if (isFullscreen) {
                        binding.youtubePlayer.exitFullScreen()
                        isFullscreen = false
                        showSystemUI()
                        showToolbar()
                    } else {
                        binding.youtubePlayer.enterFullScreen()
                        isFullscreen = true
                        hideSystemUI()
                        hideToolbar()
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

    private fun initObserver() {
        viewLifecycleOwner.observe {
            viewModel.trailerId.collectLatest {
                it?.let {
                    setupPlayer(it)
                }
            }
        }
    }
}
