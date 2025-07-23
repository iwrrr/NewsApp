package com.example.newsapp.domain.model

data class News(
    val id: Int,
    val title: String,
    val author: String,
    val publishedAt: String,
    val imageUrl: String,
    val url: String,
)