package com.alibasoglu.cinemax.utils

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
fun showBlurEffect(activity: Activity) {
    activity.window.decorView.setRenderEffect(
        RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.CLAMP)
    )
}

@RequiresApi(Build.VERSION_CODES.S)
fun clearBlurEffect(activity: Activity) {
    activity.window.decorView.setRenderEffect(null)
}
