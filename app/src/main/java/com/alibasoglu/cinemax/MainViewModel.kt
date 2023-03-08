package com.alibasoglu.cinemax

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.domain.usecase.SetImagesConfigDataUseCase
import com.alibasoglu.cinemax.ui.onboarding.OnboardingViewModel.Companion.ONBOARDING_KEY
import com.alibasoglu.cinemax.utils.ENGLISH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun getCurrentLocale(): String {
        return sharedPreferences.getString("locale", ENGLISH) ?: ENGLISH
    }

}
