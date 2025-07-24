package com.example.newsapp.infrastructure.mapper

import com.example.newsapp.domain.model.News
import com.example.newsapp.infrastructure.network.response.NewsResponse
import com.example.newsapp.infrastructure.persistence.entity.NewsEntity

object NewsMapper {
    fun NewsResponse.ArticleDto.toDomain(): News {
        return News(
            id = title.orEmpty() + publishedAt.orEmpty() + url.orEmpty(),
            title = title ?: "-",
            description = description ?: "-",
            author = author ?: "-",
            publishedAt = publishedAt.orEmpty(),
            imageUrl = urlToImage.orEmpty(),
            url = url.orEmpty(),
            source = source?.name.orEmpty(),
        )
    }

    fun NewsEntity.toDomain(): News {
        return News(
            id = id,
            title = title,
            description = description,
            author = author,
            publishedAt = publishedAt,
            imageUrl = imageUrl,
            url = url,
            source = source,
        )
    }

    fun News.toEntity(): NewsEntity {
        return NewsEntity(
            id = id,
            title = title,
            description = description,
            author = author,
            publishedAt = publishedAt,
            imageUrl = imageUrl,
            url = url,
            source = source,
        )
    }
}