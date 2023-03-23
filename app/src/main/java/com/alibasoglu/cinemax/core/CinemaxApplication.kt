package com.alibasoglu.cinemax.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CinemaxApplication : Application() {
    companion object {
        lateinit var instance: CinemaxApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
