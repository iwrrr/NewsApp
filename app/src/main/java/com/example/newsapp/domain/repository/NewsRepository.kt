package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.News
import com.example.newsapp.utils.state.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(
        query: String? = null,
        page: Int? = null,
        pageSize: Int? = null
    ): Flow<DataState<List<News>>>
}