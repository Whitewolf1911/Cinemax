package com.alibasoglu.cinemax.moviedetail.data.remote.model.movie

import com.alibasoglu.cinemax.moviedetail.domain.model.CastCrewPerson

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int?,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
)

fun Cast.mapToCastCrewPerson(): CastCrewPerson {
    return CastCrewPerson(
        id = id,
        job = known_for_department,
        name = name,
        profile_path = profile_path
    )
}
