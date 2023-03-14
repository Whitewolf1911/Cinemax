package com.alibasoglu.cinemax.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail

@Entity(tableName = "show_entities")
data class ShowEntity(
    @PrimaryKey val id: Int,
    val backdrop_path: String?,
    val episode_run_time: Int,
    val first_air_date: String,
    val genres: String,
    val name: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val original_name: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double,
)

fun ShowEntity.mapToTvShowDetail(): TvShowDetail {
    return TvShowDetail(
        id = id,
        backdrop_path = backdrop_path,
        episode_run_time = episode_run_time,
        first_air_date = first_air_date,
        genres = genres,
        name = name,
        number_of_episodes = number_of_episodes,
        number_of_seasons = number_of_seasons,
        original_name = original_name,
        overview = overview,
        poster_path = poster_path,
        vote_average = vote_average
    )
}
