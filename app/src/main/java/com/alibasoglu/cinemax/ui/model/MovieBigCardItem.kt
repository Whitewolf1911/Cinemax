package com.alibasoglu.cinemax.ui.model

import com.alibasoglu.cinemax.utils.list.RecyclerListItem

data class MovieBigCardItem(
    val id: Int,
    val genre: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
) : RecyclerListItem {
    override fun areItemsTheSame(other: RecyclerListItem): Boolean {
        return other is MovieBigCardItem && other.id == id
    }

    override fun areContentsTheSame(other: RecyclerListItem): Boolean {
        return other is MovieBigCardItem && other == this
    }
}
