package com.alibasoglu.cinemax.utils

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build


fun showBlurEffect(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        activity.window.decorView.setRenderEffect(
            RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.CLAMP)
        )
    }
}

fun clearBlurEffect(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        activity.window.decorView.setRenderEffect(null)
    }
}
