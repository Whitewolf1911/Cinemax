package com.alibasoglu.cinemax.home.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.model.mapToMovieBasicCardItem
import com.alibasoglu.cinemax.domain.usecase.GetMoviesPagerUseCase
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesPagerUseCase: GetMoviesPagerUseCase
) : BaseViewModel() {

    private val _popularMoviesState = MutableStateFlow<PagingData<MovieBasicCardItem>>(PagingData.empty())
    val popularMoviesState: StateFlow<PagingData<MovieBasicCardItem>>
        get() = _popularMoviesState

    init {
        getPopularMovies()
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

}
