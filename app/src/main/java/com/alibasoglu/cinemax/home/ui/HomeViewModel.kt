package com.alibasoglu.cinemax.home.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.model.mapToCarouselMovieItem
import com.alibasoglu.cinemax.domain.model.mapToMovieBasicCardItem
import com.alibasoglu.cinemax.domain.usecase.GetMoviesPagerUseCase
import com.alibasoglu.cinemax.home.ui.model.CarouselMovieItem
import com.alibasoglu.cinemax.home.usecase.GetCarouselMoviesUseCase
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.alibasoglu.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesPagerUseCase: GetMoviesPagerUseCase,
    private val getCarouselMoviesUseCase: GetCarouselMoviesUseCase
) : BaseViewModel() {

    private val _popularMoviesState = MutableStateFlow<PagingData<MovieBasicCardItem>>(PagingData.empty())
    val popularMoviesState: StateFlow<PagingData<MovieBasicCardItem>>
        get() = _popularMoviesState

    private val _upcomingMoviesState = MutableStateFlow<List<CarouselMovieItem>>(listOf())
    val upcomingMoviesState: StateFlow<List<CarouselMovieItem>>
        get() = _upcomingMoviesState

    init {
        getPopularMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getMoviesPagerUseCase()
                .flow
                .cachedIn(viewModelScope)
                .collectLatest { movieList ->
                    _popularMoviesState.value = movieList.map { movie ->
                        movie.mapToMovieBasicCardItem()
                    }
                }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            getCarouselMoviesUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listOfMovies ->
                            _upcomingMoviesState.value = listOfMovies.map { movie ->
                                movie.mapToCarouselMovieItem()
                            }
                        }
                    }
                    is Resource.Error -> {
                        //TODO error state
                    }
                    is Resource.Loading -> {
                        //TODO handle loading state
                    }
                }
            }
        }
    }

}
