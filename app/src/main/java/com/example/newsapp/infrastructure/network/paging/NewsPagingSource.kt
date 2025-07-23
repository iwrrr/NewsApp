package com.example.newsapp.infrastructure.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.domain.model.News
import com.example.newsapp.infrastructure.mapper.NewsMapper.toDomain
import com.example.newsapp.infrastructure.network.service.NewsApiService

class NewsPagingSource(
    private val newsApiService: NewsApiService,
    private val query: String,
) : PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.searchNews(
                query = query,
                page = page
            )
            val news = response.articles?.map { it.toDomain() }.orEmpty()

            LoadResult.Page(
                data = news,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (news.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}