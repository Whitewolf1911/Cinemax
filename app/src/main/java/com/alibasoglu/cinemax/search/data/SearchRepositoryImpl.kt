package com.alibasoglu.cinemax.search.data

import com.alibasoglu.cinemax.search.data.model.mapToPersonItem
import com.alibasoglu.cinemax.search.domain.SearchRepository
import com.alibasoglu.cinemax.search.ui.model.PersonItem
import com.alibasoglu.cinemax.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SearchRepositoryImpl(
    private val searchApi: SearchApi
) : SearchRepository {

    override suspend fun searchPerson(searchQuery: String): Flow<Resource<List<PersonItem>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                searchApi.searchPerson(searchQuery).body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.results?.let { personResultList ->
                val personItemList = personResultList.map {
                    it.mapToPersonItem()
                }
                emit(Resource.Success(data = personItemList))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }
}
