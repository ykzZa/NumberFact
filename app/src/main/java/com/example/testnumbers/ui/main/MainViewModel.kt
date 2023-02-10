package com.example.testnumbers.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testnumbers.data.ProjectDatabase
import com.example.testnumbers.data.api.ApiService
import com.example.testnumbers.data.api.RetrofitBuilder
import com.example.testnumbers.data.search.Search
import com.example.testnumbers.data.search.SearchRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository: SearchRepository
    private val typesList = listOf("trivia", "year", "date", "math")
    val getSearches: LiveData<List<Search>>

    init {
        val searchDao = ProjectDatabase.getDatabase(application)
        repository = SearchRepository(searchDao)
        getSearches = repository.getSearches()
    }

    fun onGetButtonClicked(number: String) {
        viewModelScope.launch {
            val response = repository.getFact(number, typesList.random())
            if(response.isSuccessful) {
                repository.insertSearch(response.body()!!)
            }
        }
    }

    fun onGetRandomButtonClicked() {
        viewModelScope.launch {
            val response = repository.getRandomFact(typesList.random())
            if(response.isSuccessful) {
                repository.insertSearch(response.body()!!)
            }
        }
    }
}