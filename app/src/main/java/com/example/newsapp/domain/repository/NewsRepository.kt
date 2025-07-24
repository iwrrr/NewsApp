package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.domain.model.News
import com.example.newsapp.utils.state.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<DataState<List<News>>>

    fun searchNews(
        query: String,
    ): Flow<PagingData<News>>

    suspend fun saveNews(news: News): DataState<Unit>

    suspend fun deleteNews(news: News): DataState<Unit>

    fun getViewedNews(): Flow<DataState<List<News>>>
}