package com.alibasoglu.cinemax.utils

import android.util.Patterns
import androidx.annotation.StringRes
import com.alibasoglu.cinemax.core.CinemaxApplication

object Strings {
    /**
     * This function is for getting the string resources outside of context scope
     */
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return CinemaxApplication.instance.getString(stringRes, *formatArgs)
    }
}

/**
 * Returns true if string is valid email pattern
 */
fun String.isValidEmail(): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(this).matches()
}
