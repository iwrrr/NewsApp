package com.example.newsapp.infrastructure.network.service

import com.example.newsapp.infrastructure.network.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
    ) : NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String?,
        @Query("page") page: Int?,
    ) : NewsResponse
}