package com.alibasoglu.cinemax.moviedetail.data.remote.model.tv

import com.alibasoglu.cinemax.moviedetail.ui.model.EpisodeItem

data class Episode(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val runtime: Int,
    val season_number: Int,
    val show_id: Int,
    val still_path: String,
)


fun Episode.mapToEpisodeItem(): EpisodeItem {
    return EpisodeItem(
        episodeNumber = episode_number,
        id = id,
        name = name,
        overview = overview,
        runtime = runtime,
        seasonNumber = season_number,
        showId = show_id,
        stillPath = still_path
    )
}
