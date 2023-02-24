package com.alibasoglu.cinemax

import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.usecase.SetImagesConfigDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setImagesConfigDataUseCase: SetImagesConfigDataUseCase
) : BaseViewModel() {

    fun getSetImagesConfigData() {
        viewModelScope.launch {
            setImagesConfigDataUseCase()
        }
    }

}
