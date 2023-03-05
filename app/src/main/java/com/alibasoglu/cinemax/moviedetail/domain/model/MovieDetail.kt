package com.alibasoglu.cinemax.moviedetail.domain.model

import android.os.Parcelable
import com.alibasoglu.cinemax.data.local.model.MovieEntity
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val backdrop_path: String?,
    val genre: String,
    val id: Int,
    val original_title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String,
    val runtime: Int,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
) : Parcelable

fun MovieDetail.mapToMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        backdrop_path = backdrop_path ?: "",
        genre = genre,
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

fun MovieDetail.mapToWishListCardItem(): WishListCardItem {
    return WishListCardItem(
        backdrop_path = backdrop_path ?: "",
        genre = genre,
        id = id,
        title = title,
        vote_average = vote_average
    )
}
