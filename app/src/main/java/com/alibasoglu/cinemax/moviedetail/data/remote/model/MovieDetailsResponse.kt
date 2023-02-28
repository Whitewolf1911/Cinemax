package com.alibasoglu.cinemax.moviedetail.data.remote.model

import com.alibasoglu.cinemax.model.Genre
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail

data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val revenue: Any,
    val runtime: Int,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailsResponse.mapToMovieDetail(): MovieDetail {
    return MovieDetail(
        backdrop_path = backdrop_path,
        genre = genres[0].name,
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
