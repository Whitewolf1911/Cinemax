package com.alibasoglu.cinemax.search.ui

import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.model.mapToMovieBasicCardItem
import com.alibasoglu.cinemax.domain.model.mapToMovieBigCardItem
import com.alibasoglu.cinemax.search.domain.usecase.GetMovieOfTheDayUseCase
import com.alibasoglu.cinemax.search.domain.usecase.GetRecommendedMoviesUseCase
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.alibasoglu.cinemax.ui.model.MovieBigCardItem
import com.alibasoglu.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase,
    private val getMovieOfTheDayUseCase: GetMovieOfTheDayUseCase
) : BaseViewModel() {

    private var _recommendedMoviesState = MutableStateFlow<List<MovieBasicCardItem>>(listOf())
    val recommendedMoviesState: StateFlow<List<MovieBasicCardItem>>
        get() = _recommendedMoviesState

    private var _movieOfTheDayState = MutableStateFlow<MovieBigCardItem>(
        MovieBigCardItem(
            id = 0,
            genre = "",
            poster_path = "",
            release_date = "",
            title = "",
            vote_average = 0.0
        )
    )
    val movieOfTheDayState: StateFlow<MovieBigCardItem>
        get() = _movieOfTheDayState


    init {
        getMovieOfTheDay()
        getRecommendedMovies()
    }

    private fun getRecommendedMovies() {
        viewModelScope.launch {
            getRecommendedMoviesUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { movieList ->
                            _recommendedMoviesState.value = movieList.map { movie ->
                                movie.mapToMovieBasicCardItem()
                            }
                        }
                    }
                    is Resource.Error -> {
                        //TODO error handling
                    }
                    is Resource.Loading -> {
                        //TODO loading handling
                    }
                }
            }
        }
    }

    private fun getMovieOfTheDay() {
        viewModelScope.launch {
            getMovieOfTheDayUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { movie ->
                            _movieOfTheDayState.value = movie.mapToMovieBigCardItem()
                        }
                    }
                    is Resource.Error -> {
                        //TODO error handling
                    }
                    is Resource.Loading -> {
                        //TODO loading handling
                    }
                }
            }
        }
    }

}
