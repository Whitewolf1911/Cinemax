package com.alibasoglu.cinemax.data.remote.model

data class ConfigurationResponse(
    val change_keys: List<String>,
    val images: ImagesConfigurationsData
)
