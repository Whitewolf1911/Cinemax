package com.alibasoglu.cinemax.home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.BaseFragment
import com.alibasoglu.cinemax.core.fragment.FragmentConfiguration
import com.alibasoglu.cinemax.databinding.FragmentHomeBinding
import com.alibasoglu.cinemax.home.ui.model.CarouselMovieItem
import com.alibasoglu.cinemax.ui.MoviesBasicCardAdapter
import com.alibasoglu.cinemax.utils.lifecycle.observe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    var dummyCarouselData = listOf(
        CarouselMovieItem(
            id = "1",
            title = "Spider Man",
            upcomingDate = "On April 3",
            imageUrl = "https://images.pling.com/img/00/00/61/26/90/1576052/35b4a108977b67bfdcc8ed854aa5261482712cd8822953bd42d51123e375f3c00513.jpg"
        ),
        CarouselMovieItem(
            id = "2",
            title = "X-Men Origins : Wolverine",
            upcomingDate = "On April 5",
            imageUrl = "https://m.media-amazon.com/images/I/91cV4E3r05L._AC_SX569_.jpg"
        ),
        CarouselMovieItem(
            id = "3",
            title = "Coraline",
            upcomingDate = "On April 4",
            imageUrl = "https://cdn.wannart.com/production/post/iki-dunya-arasinda-bir-animasyon-coraline-FxXng.jpg"
        )
    )

    private val carouselAdapter = MoviesCarouselAdapter()

    private val moviesBasicCardAdapter = MoviesBasicCardAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        showBottomNavbar()
        with(binding) {
            carouselRecyclerView.apply {
                this.adapter = carouselAdapter
                set3DItem(true)
                setAlpha(true)
            }
            popularMoviesRecyclerView.adapter = moviesBasicCardAdapter
            carouselAdapter.submitList(dummyCarouselData)

            seeAllTextView.setOnClickListener {
                //TODO nav to most popular fragment
            }

            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("All"))
            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("Comedy"))
            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("Animation"))
            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("Document"))
            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("Action"))
            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("Tab 6"))
            categoriesTabLayout.addTab(categoriesTabLayout.newTab().setText("Tab 7"))

        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.popularMoviesState.collectLatest {
                moviesBasicCardAdapter.submitData(it)
            }
        }
    }
}
