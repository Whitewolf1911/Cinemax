package com.alibasoglu.cinemax.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

fun Context.setAppLocale(language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    resources.updateConfiguration(config, resources.displayMetrics)
}
