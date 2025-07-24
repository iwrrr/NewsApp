package com.example.newsapp.domain.model

data class News(
    val id: String,
    val title: String,
    val description: String,
    val author: String,
    val publishedAt: String,
    val imageUrl: String,
    val url: String,
    val source: String,
)