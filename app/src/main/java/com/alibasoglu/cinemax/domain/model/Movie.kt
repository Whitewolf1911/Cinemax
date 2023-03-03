package com.alibasoglu.cinemax.domain.model

import com.alibasoglu.cinemax.getGenreName
import com.alibasoglu.cinemax.home.ui.model.CarouselMovieItem
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.alibasoglu.cinemax.ui.model.MovieBigCardItem

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

fun Movie.mapToMovieBigCardItem(): MovieBigCardItem {
    return MovieBigCardItem(
        id = id,
        genre = getGenreName(genre_ids.firstOrNull() ?: 0),
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average
    )
}
