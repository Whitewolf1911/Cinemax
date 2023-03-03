package com.alibasoglu.cinemax.moviedetail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieTrailerUseCase
import com.alibasoglu.cinemax.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieTrailerViewModel @Inject constructor(
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val movieId = savedStateHandle.getOrThrow<Int>("movieId")

    private var _trailerId = MutableStateFlow<String?>(null)
    val trailerId: StateFlow<String?>
        get() = _trailerId

    init {
        getMovieTrailer()
    }

    private fun getMovieTrailer() {
        viewModelScope.launch {
            _trailerId.value = getMovieTrailerUseCase(movieId).key
        }
    }
}
