package com.alibasoglu.cinemax.search.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.data.remote.pagingsource.PagingDataType
import com.alibasoglu.cinemax.domain.model.mapToMovieBigCardItem
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.domain.usecase.GetMoviesPagerUseCase
import com.alibasoglu.cinemax.ui.model.MovieBigCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedMoviesViewModel @Inject constructor(
    private val getMoviesPagerUseCase: GetMoviesPagerUseCase,
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    private val _recommendedMoviesState = MutableStateFlow<PagingData<MovieBigCardItem>>(PagingData.empty())
    val recommendedMoviesState: StateFlow<PagingData<MovieBigCardItem>>
        get() = _recommendedMoviesState


    init {
        getRecommendedMovies()
    }

    private fun getRecommendedMovies() {
        viewModelScope.launch {
            val randomMovieId = moviesRepository.getRandomWishListedMovieId()
            getMoviesPagerUseCase(PagingDataType.RecommendedMovies(randomMovieId))
                .flow
                .cachedIn(viewModelScope)
                .collectLatest { movieList ->
                    _recommendedMoviesState.value = movieList.map {
                        it.mapToMovieBigCardItem()
                    }
                }
        }

    }
}
