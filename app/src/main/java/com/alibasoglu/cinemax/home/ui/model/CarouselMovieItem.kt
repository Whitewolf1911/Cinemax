package com.alibasoglu.cinemax.home.ui.model

import com.alibasoglu.cinemax.utils.list.RecyclerListItem

data class CarouselMovieItem(
    val id: String,
    val title: String,
    val upcomingDate: String,
    val imageUrl: String
) : RecyclerListItem {
    override fun areItemsTheSame(other: RecyclerListItem): Boolean {
        return other is CarouselMovieItem && other.id == id
    }

    override fun areContentsTheSame(other: RecyclerListItem): Boolean {
        return other is CarouselMovieItem && other == this
    }
}
