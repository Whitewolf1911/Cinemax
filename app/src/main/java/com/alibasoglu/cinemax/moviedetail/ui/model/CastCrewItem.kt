package com.alibasoglu.cinemax.moviedetail.ui.model

import com.alibasoglu.cinemax.utils.list.RecyclerListItem

data class CastCrewItem(
    val id: String,
    val name: String,
    val profilePathUrl: String?,
    val job: String
) : RecyclerListItem {
    override fun areItemsTheSame(other: RecyclerListItem): Boolean {
        return other is CastCrewItem && other.id == id
    }

    override fun areContentsTheSame(other: RecyclerListItem): Boolean {
        return other is CastCrewItem && other == this
    }
}
