package com.example.testnumbers.data.api

import com.example.testnumbers.data.search.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("random/{factType}?json")
    suspend fun getRandomFact(@Path("factType") factType: String): Response<Search>

    @GET("{number}/{factType}?json")
    suspend fun getFact(@Path("number") number: String,
                        @Path("factType") factType: String): Response<Search>
}