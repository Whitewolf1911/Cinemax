package com.alibasoglu.cinemax.ui.wishlist

import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.usecase.GetWishListedMoviesUseCase
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getWishListedMoviesUseCase: GetWishListedMoviesUseCase
) : BaseViewModel() {


    private var _wishListedMoviesState = MutableStateFlow<List<WishListCardItem>>(listOf())
    val wishListedMoviesState: StateFlow<List<WishListCardItem>>
        get() = _wishListedMoviesState

    fun getWishListedMovies() {
        viewModelScope.launch {
            getWishListedMoviesUseCase().collectLatest {
                _wishListedMoviesState.value = it
            }
        }
    }


}
