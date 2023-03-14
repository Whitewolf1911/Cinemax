package com.alibasoglu.cinemax.moviedetail.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.usecase.CheckMovieWishListedUseCase
import com.alibasoglu.cinemax.domain.usecase.InsertMovieToDatabaseUseCase
import com.alibasoglu.cinemax.domain.usecase.RemoveMovieFromDatabaseUseCase
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.mapToCastCrewItem
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieCastCrewListUseCase
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieDetailsUseCase
import com.alibasoglu.cinemax.moviedetail.ui.model.CastCrewItem
import com.alibasoglu.cinemax.moviedetail.ui.model.MovieDetailState
import com.alibasoglu.cinemax.utils.Resource
import com.alibasoglu.cinemax.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastCrewListUseCase: GetMovieCastCrewListUseCase,
    private val insertMovieToDatabaseUseCase: InsertMovieToDatabaseUseCase,
    private val removeMovieFromDatabaseUseCase: RemoveMovieFromDatabaseUseCase,
    private val checkMovieWishListedUseCase: CheckMovieWishListedUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val movieId = savedStateHandle.getOrThrow<Int>("movieId")

    private var _movieDetailsState =
        MutableStateFlow(MovieDetailState(isLoading = true, movieDetail = null))
    val movieDetailsState: StateFlow<MovieDetailState>
        get() = _movieDetailsState

    var wishListedState = MutableStateFlow(false)

    private var movieData: MovieDetail? = null

    private var _movieCastCrewList = MutableStateFlow<List<CastCrewItem>>(listOf())
    val movieCastCrewList: StateFlow<List<CastCrewItem>>
        get() = _movieCastCrewList

    init {
        getMovieDetails()
        getMovieCastCrewList()
        checkMovieWishListed()
    }


    fun getMovieDetails() {
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
            movieData?.let {
                insertMovieToDatabaseUseCase(it)
                wishListedState.value = true
            }
        }
    }

    fun removeMovieFromDatabase() {
        viewModelScope.launch {
            removeMovieFromDatabaseUseCase(movieId)
            wishListedState.value = false
        }
    }

    private fun checkMovieWishListed() {
        viewModelScope.launch {
            wishListedState.value = checkMovieWishListedUseCase(movieId)
        }
    }

}
