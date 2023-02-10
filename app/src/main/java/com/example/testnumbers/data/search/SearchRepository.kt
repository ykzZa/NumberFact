package com.example.testnumbers.data.search

import androidx.lifecycle.LiveData
import com.example.testnumbers.data.ProjectDatabase
import com.example.testnumbers.data.api.RetrofitBuilder
import retrofit2.Response

class SearchRepository(
    private val database: ProjectDatabase
) {

    suspend fun insertSearch(search: Search){
       database.searchDao().insert(search)
    }

    suspend fun getRandomFact(factType: String): Response<Search> =
        RetrofitBuilder.apiService.getRandomFact(factType)


    suspend fun getFact(number: String, factType: String): Response<Search> =
        RetrofitBuilder.apiService.getFact(number, factType)


    fun getSearches(): LiveData<List<Search>> = database.searchDao().getSearches()
}