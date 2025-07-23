package com.example.newsapp.presentation.home.utils

import com.example.newsapp.domain.model.News

sealed class NewsContentType {
    data class Row(val news: News) : NewsContentType()
    data class Grid(val news: News) : NewsContentType()
}