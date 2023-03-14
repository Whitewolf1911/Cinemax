package com.alibasoglu.cinemax.ui.wishlist

import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.usecase.GetWishListedMediaUseCase
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getWishListedMediaUseCase: GetWishListedMediaUseCase
) : BaseViewModel() {

    private var _wishListedMoviesState = MutableStateFlow<List<WishListCardItem>>(listOf())
    val wishListedMoviesState: StateFlow<List<WishListCardItem>>
        get() = _wishListedMoviesState

    fun getWishListedMovies() {
        viewModelScope.launch {
            getWishListedMediaUseCase().collectLatest {
                _wishListedMoviesState.value = it
            }
        }
    }
}
