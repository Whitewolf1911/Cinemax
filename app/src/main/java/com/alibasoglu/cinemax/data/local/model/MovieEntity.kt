package com.alibasoglu.cinemax.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail

@Entity(tableName = "movie_entities")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val backdrop_path: String,
    val genre: String,
    val original_title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String,
    val runtime: Int,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
)

fun MovieEntity.mapToMovieDetail(): MovieDetail {
    return MovieDetail(
        backdrop_path = backdrop_path,
        genre = genre,
        id = id,
        original_title = original_title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        runtime = runtime,
        title = title,
        video = video,
        vote_average = vote_average
    )
}
