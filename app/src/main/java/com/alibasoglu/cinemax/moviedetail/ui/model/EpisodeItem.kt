package com.alibasoglu.cinemax.moviedetail.ui.model

import com.alibasoglu.cinemax.utils.list.RecyclerListItem

data class EpisodeItem(
    val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val runtime: Int,
    val seasonNumber: Int,
    val showId: Int,
    val stillPath: String,
) : RecyclerListItem {
    override fun areItemsTheSame(other: RecyclerListItem): Boolean {
        return other is EpisodeItem && other.id == id
    }

    override fun areContentsTheSame(other: RecyclerListItem): Boolean {
        return other is EpisodeItem && other == this
    }
}
