package com.example.testnumbers.data.search

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchDao {

    @Insert
    suspend fun insert(search: Search)

    @Query("SELECT * FROM search_table")
    fun getSearches(): LiveData<List<Search>>
}