package com.example.newsapp.infrastructure.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapp.infrastructure.persistence.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getViewedNews(): Flow<List<NewsEntity>>

    @Upsert
    suspend fun upsertNews(news: NewsEntity)

    @Delete
    suspend fun deleteNews(news: NewsEntity)
}