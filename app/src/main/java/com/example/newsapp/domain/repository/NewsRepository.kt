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
}