package com.example.newsapp.infrastructure.network.service

import com.example.newsapp.infrastructure.network.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") query: String?,
        @Query("country") country: String = "us",
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?,
    ) : NewsResponse
}