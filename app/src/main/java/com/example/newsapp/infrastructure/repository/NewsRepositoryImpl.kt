package com.example.newsapp.infrastructure.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.infrastructure.mapper.NewsMapper.toDomain
import com.example.newsapp.infrastructure.mapper.NewsMapper.toEntity
import com.example.newsapp.infrastructure.network.paging.NewsPagingSource
import com.example.newsapp.infrastructure.network.response.ErrorResponse
import com.example.newsapp.infrastructure.network.service.NewsApiService
import com.example.newsapp.infrastructure.persistence.NewsDao
import com.example.newsapp.utils.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class NewsRepositoryImpl(
    private val apiService: NewsApiService,
    private val newsDao: NewsDao,
) : NewsRepository {

    override fun getNews(): Flow<DataState<List<News>>> = flow {
        try {
            val response = apiService.getNews()

            val newsList = response.articles?.map { it.toDomain() }.orEmpty()
            emit(DataState.Success(newsList))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = errorBody?.let {
                try {
                    val errorResponse = Json.decodeFromString<ErrorResponse>(it)
                    errorResponse.message
                } catch (_: Exception) {
                    "Unexpected error format"
                }
            } ?: e.localizedMessage ?: "Unknown HTTP error"

            emit(DataState.Error(Exception(errorMessage)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun searchNews(query: String): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 2 * 20,
                initialLoadSize = 1 * 20,
            ),
            pagingSourceFactory = { NewsPagingSource(apiService, query) }
        ).flow
    }

    override suspend fun saveNews(news: News): DataState<Unit> {
        return try {
            newsDao.upsertNews(news.toEntity())
            DataState.Success(Unit)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    override suspend fun deleteNews(news: News): DataState<Unit> {
        return try {
            newsDao.deleteNews(news.toEntity())
            DataState.Success(Unit)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    override fun getViewedNews(): Flow<DataState<List<News>>> {
        return newsDao.getViewedNews().map { data ->
            val newsList = data.map { it.toDomain() }
            DataState.Success(newsList)
        }.catch {
            DataState.Error(it)
        }.flowOn(Dispatchers.IO)
    }
}