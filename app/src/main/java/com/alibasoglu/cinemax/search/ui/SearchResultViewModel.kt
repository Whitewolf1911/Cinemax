package com.alibasoglu.cinemax.search.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.search.data.model.PersonListState
import com.alibasoglu.cinemax.search.domain.usecase.SearchPersonUseCase
import com.alibasoglu.cinemax.utils.Resource
import com.alibasoglu.cinemax.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchPersonUseCase: SearchPersonUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val firstQuery = savedStateHandle.getOrThrow<String>("searchQuery")

    private var _personListState = MutableStateFlow(PersonListState(isLoading = true, emptyList()))
    val personListState: StateFlow<PersonListState>
        get() = _personListState

    init {
        searchPerson(firstQuery)
    }

    fun searchPerson(searchQuery: String) {
        viewModelScope.launch {
            searchPersonUseCase(searchQuery).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _personListState.value = PersonListState(isLoading = false, personItemList = it)
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
