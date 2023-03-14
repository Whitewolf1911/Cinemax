package com.alibasoglu.cinemax.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

fun Context.setAppLocale(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
    return createConfigurationContext(config)
}
