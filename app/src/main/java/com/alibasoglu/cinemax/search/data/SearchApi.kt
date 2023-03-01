package com.alibasoglu.cinemax.search.data

import com.alibasoglu.cinemax.search.data.model.PersonSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/person")
    suspend fun searchPerson(
        @Query("query") searchQuery: String
    ): Response<PersonSearchResult>


}
