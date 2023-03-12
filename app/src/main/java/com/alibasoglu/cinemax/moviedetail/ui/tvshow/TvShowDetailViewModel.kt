package com.alibasoglu.cinemax.moviedetail.ui.tvshow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.moviedetail.domain.model.mapToCastCrewItem
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetTvShowCastCrewListUseCase
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetTvShowDetailsUseCase
import com.alibasoglu.cinemax.moviedetail.ui.model.CastCrewItem
import com.alibasoglu.cinemax.moviedetail.ui.model.TvShowDetailState
import com.alibasoglu.cinemax.utils.Resource
import com.alibasoglu.cinemax.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getTvShowCastCrewListUseCase: GetTvShowCastCrewListUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val showId = savedStateHandle.getOrThrow<Int>("showId")

    private var _showDetailState =
        MutableStateFlow(TvShowDetailState(isLoading = true, tvShowDetail = null))
    val showDetailState: StateFlow<TvShowDetailState>
        get() = _showDetailState

    private var _movieCastCrewList = MutableStateFlow<List<CastCrewItem>>(listOf())
    val movieCastCrewList: StateFlow<List<CastCrewItem>>
        get() = _movieCastCrewList

    fun getTvShowDetails() {
        viewModelScope.launch {
            getTvShowDetailsUseCase(showId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { detail ->
                            _showDetailState.value =
                                _showDetailState.value.copy(tvShowDetail = detail, isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _showDetailState.value =
                            _showDetailState.value.copy(tvShowDetail = null, isLoading = false)
                    }
                    is Resource.Loading -> {
                        _showDetailState.value = _showDetailState.value.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    fun getShowCastCrew() {
        viewModelScope.launch {
            getTvShowCastCrewListUseCase(showId).collectLatest { result ->
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
}
