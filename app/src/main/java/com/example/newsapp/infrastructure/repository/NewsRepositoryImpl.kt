package com.example.newsapp.infrastructure.repository

import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.infrastructure.mapper.NewsMapper.toDomain
import com.example.newsapp.infrastructure.network.response.ErrorResponse
import com.example.newsapp.infrastructure.network.service.NewsApiService
import com.example.newsapp.utils.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class NewsRepositoryImpl(
    private val apiService: NewsApiService,
) : NewsRepository {

    override suspend fun getNews(
        query: String?,
        page: Int?,
        pageSize: Int?
    ): Flow<DataState<List<News>>> = flow {
        try {
            val response = apiService.getNews(
                query = query,
                page = page,
                pageSize = pageSize
            )

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
}