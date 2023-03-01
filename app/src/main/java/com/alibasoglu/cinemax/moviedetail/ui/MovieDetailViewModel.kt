package com.alibasoglu.cinemax.moviedetail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.usecase.InsertMovieToDatabaseUseCase
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.mapToCastCrewItem
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieCastCrewListUseCase
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieDetailsUseCase
import com.alibasoglu.cinemax.moviedetail.ui.model.CastCrewItem
import com.alibasoglu.cinemax.moviedetail.ui.model.MovieDetailState
import com.alibasoglu.cinemax.utils.Resource
import com.alibasoglu.cinemax.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastCrewListUseCase: GetMovieCastCrewListUseCase,
    private val insertMovieToDatabaseUseCase: InsertMovieToDatabaseUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val movieId = savedStateHandle.getOrThrow<Int>("movieId")

    private var _movieDetailsState =
        MutableStateFlow<MovieDetailState>(MovieDetailState(isLoading = true, movieDetail = null))
    val movieDetailsState: StateFlow<MovieDetailState>
        get() = _movieDetailsState

    private var movieData: MovieDetail? = null

    private var _movieCastCrewList = MutableStateFlow<List<CastCrewItem>>(listOf())
    val movieCastCrewList: StateFlow<List<CastCrewItem>>
        get() = _movieCastCrewList

    init {
        getMovieDetails()
        getMovieCastCrewList()
    }


    private fun getMovieDetails() {
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _movieDetailsState.value = MovieDetailState(isLoading = false, movieDetail = it)
                            movieData = it
                        }
                    }
                    is Resource.Error -> {
                        //TODO error handle
                    }
                    is Resource.Loading -> {
                        _movieDetailsState.value = MovieDetailState(isLoading = result.isLoading, movieDetail = null)
                    }
                }
            }
        }
    }

    private fun getMovieCastCrewList() {
        viewModelScope.launch {
            getMovieCastCrewListUseCase(movieId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { list ->
                            _movieCastCrewList.value = list.map {
                                it.mapToCastCrewItem()
                            }
                        }
                    }
                    is Resource.Error -> {
                        //TODO error handle
                    }
                    is Resource.Loading -> {
                        //TODO loading handle
                    }
                }
            }
        }
    }

    fun insertMovieToDatabase() {
        viewModelScope.launch {
            movieData?.let { insertMovieToDatabaseUseCase(it) }
        }
    }

}
