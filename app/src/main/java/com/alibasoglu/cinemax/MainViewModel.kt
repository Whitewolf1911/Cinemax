package com.alibasoglu.cinemax

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.usecase.SetImagesConfigDataUseCase
import com.alibasoglu.cinemax.ui.onboarding.OnboardingViewModel.Companion.ONBOARDING_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setImagesConfigDataUseCase: SetImagesConfigDataUseCase,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    fun getSetImagesConfigData() {
        viewModelScope.launch {
            setImagesConfigDataUseCase()
        }
    }

    fun getShouldShowOnBoarding(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDING_KEY, true)
    }

}
