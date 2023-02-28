package com.alibasoglu.cinemax.moviedetail.domain.model

import com.alibasoglu.cinemax.moviedetail.ui.model.CastCrewItem

data class CastCrewPerson(
    val id: Int,
    val job: String,
    val name: String,
    val profile_path: String?
)

fun CastCrewPerson.mapToCastCrewItem(): CastCrewItem {
    return CastCrewItem(
        id = id,
        name = name,
        profilePathUrl = profile_path,
        job = job
    )
}
