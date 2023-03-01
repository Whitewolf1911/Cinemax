package com.alibasoglu.cinemax.search.ui.model

import com.alibasoglu.cinemax.utils.list.RecyclerListItem

data class PersonItem(
    val id: Int,
    val name: String,
    val profile_path: String?
) : RecyclerListItem {
    override fun areItemsTheSame(other: RecyclerListItem): Boolean {
        return other is PersonItem && other.id == id
    }

    override fun areContentsTheSame(other: RecyclerListItem): Boolean {
        return other is PersonItem && other == this
    }
}
