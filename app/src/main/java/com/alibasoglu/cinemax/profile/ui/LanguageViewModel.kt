package com.alibasoglu.cinemax.profile.ui

import android.content.SharedPreferences
import com.alibasoglu.cinemax.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    fun getCurrentLocale(): String {
        return sharedPreferences.getString("locale", "en") ?: "en"
    }

    fun setCurrentLocale(language: String) {
        sharedPreferences.edit().putString("locale", language).apply()
    }

}
