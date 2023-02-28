package com.alibasoglu.cinemax.domain.model

import com.alibasoglu.cinemax.getGenreName
import com.alibasoglu.cinemax.home.ui.model.CarouselMovieItem
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

fun Movie.mapToMovieBasicCardItem(): MovieBasicCardItem {
    return MovieBasicCardItem(
        id = id,
        title = title,
        rating = vote_average.toString(),
        genre = getGenreName(genre_ids.firstOrNull() ?: 0), // Should refactor here (List is empty exception)
        imageUrl = poster_path
    )
}

fun Movie.mapToCarouselMovieItem(): CarouselMovieItem {
    return CarouselMovieItem(
        id = id,
        title = title,
        upcomingDate = release_date,
        imageUrl = backdrop_path
    )
}
