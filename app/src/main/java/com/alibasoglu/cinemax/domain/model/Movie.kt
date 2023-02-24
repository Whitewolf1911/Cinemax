package com.alibasoglu.cinemax.domain.model

import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem

data class Movie(
    val id: Int,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
)

// TODO setting genre
fun Movie.mapToMovieBasicCardItem(): MovieBasicCardItem {
    return MovieBasicCardItem(
        id = id,
        title = title,
        rating = vote_average.toString(),
        genre = "Action",
        imageUrl = poster_path
    )
}
