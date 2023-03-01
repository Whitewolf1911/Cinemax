package com.alibasoglu.cinemax.utils

import android.content.Context
import android.widget.Toast

fun Context.showTextToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
