package com.alibasoglu.cinemax

import com.alibasoglu.cinemax.data.remote.model.ImagesConfigurationsData

object ImagesConfigData {
    var backdrop_sizes: List<String>? = null
    var base_url: String? = null
    var logo_sizes: List<String>? = null
    var poster_sizes: List<String>? = null
    var profile_sizes: List<String>? = null
    var secure_base_url: String? = null
    var still_sizes: List<String>? = null
}

fun ImagesConfigData.setConfigDataFromResponse(imagesConfigurationsData: ImagesConfigurationsData) {
    with(imagesConfigurationsData) {
        ImagesConfigData.backdrop_sizes = backdrop_sizes
        ImagesConfigData.base_url = base_url
        ImagesConfigData.logo_sizes = logo_sizes
        ImagesConfigData.poster_sizes = poster_sizes
        ImagesConfigData.profile_sizes = profile_sizes
        ImagesConfigData.secure_base_url = secure_base_url
        ImagesConfigData.still_sizes = still_sizes
    }
}
