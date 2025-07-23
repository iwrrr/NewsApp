package com.example.newsapp.infrastructure.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("articles")
    val articles: List<ArticleDto>?,
    @SerialName("status")
    val status: String?,
    @SerialName("totalResults")
    val totalResults: Int?
) {
    @Serializable
    data class ArticleDto(
        @SerialName("author")
        val author: String?,
        @SerialName("content")
        val content: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("publishedAt")
        val publishedAt: String?,
        @SerialName("source")
        val source: SourceDto?,
        @SerialName("title")
        val title: String?,
        @SerialName("url")
        val url: String?,
        @SerialName("urlToImage")
        val urlToImage: String?
    ) {
        @Serializable
        data class SourceDto(
            @SerialName("id")
            val id: String?,
            @SerialName("name")
            val name: String?
        )
    }
}