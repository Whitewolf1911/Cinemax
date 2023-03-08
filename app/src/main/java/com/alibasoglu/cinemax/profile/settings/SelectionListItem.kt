package com.alibasoglu.cinemax.profile.settings

import android.content.Context

sealed class SelectionListItem {
    abstract var isSelected: Boolean

    abstract fun getVisibleName(context: Context): String
}

data class LanguageListItem(
    val languageId: String,
    val languageName: String,
    override var isSelected: Boolean
) : SelectionListItem() {
    override fun getVisibleName(context: Context): String = languageName
}
