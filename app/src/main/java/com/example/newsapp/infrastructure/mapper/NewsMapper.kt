package com.example.newsapp.infrastructure.mapper

import com.example.newsapp.domain.model.News
import com.example.newsapp.infrastructure.network.response.NewsResponse

object NewsMapper {
    fun NewsResponse.ArticleDto.toDomain(): News {
        return News(
            id = title.orEmpty().hashCode(),
            title = title.orEmpty(),
            author = author.orEmpty(),
            publishedAt = publishedAt.orEmpty(),
            imageUrl = urlToImage.orEmpty(),
            url = url.orEmpty(),
            source = source?.name.orEmpty(),
        )
    }
}