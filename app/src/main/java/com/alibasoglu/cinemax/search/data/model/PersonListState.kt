package com.alibasoglu.cinemax.search.data.model

import com.alibasoglu.cinemax.search.ui.model.PersonItem

data class PersonListState(
    val isLoading: Boolean = false,
    val personItemList: List<PersonItem> = emptyList()
)
