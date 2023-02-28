package com.alibasoglu.cinemax.moviedetail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.mapToCastCrewItem
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieCastCrewListUseCase
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieDetailsUseCase
import com.alibasoglu.cinemax.moviedetail.ui.model.CastCrewItem
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
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val movieId = savedStateHandle.getOrThrow<Int>("movieId")

    private var _movieDetailsState = MutableStateFlow(
        MovieDetail(
            backdrop_path = "",
            genre = "",
            id = 0,
            original_title = "",
            overview = null,
            poster_path = null,
            release_date = "",
            runtime = 0,
            title = "",
            video = false,
            vote_average = 0.0
        )
    )
    val movieDetailsState: StateFlow<MovieDetail>
        get() = _movieDetailsState

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
                            _movieDetailsState.value = it
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

    private fun getMovieCastCrewList() {
        viewModelScope.launch {
            getMovieCastCrewListUseCase(movieId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _movieCastCrewList.value = it.map {
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

}
