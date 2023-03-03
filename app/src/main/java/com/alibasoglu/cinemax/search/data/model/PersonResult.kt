package com.alibasoglu.cinemax.search.data.model

import com.alibasoglu.cinemax.search.ui.model.PersonItem

data class PersonResult(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
)

fun PersonResult.mapToPersonItem(): PersonItem {
    return PersonItem(id = id, name = name, profile_path = profile_path)
}
