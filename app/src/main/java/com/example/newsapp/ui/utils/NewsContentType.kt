package com.example.newsapp.ui.utils

import com.example.newsapp.domain.model.News

sealed class NewsContentType {
    data class Row(val news: News) : NewsContentType()
    data class Grid(val news: News) : NewsContentType()
}

fun generateDummyGridItems(): List<NewsContentType> {
    val dummyNewsList = List(20) { index ->
        News(
            id = index,
            title = "News Title #$index",
            author = "Author $index",
            publishedAt = "2025-07-${(index % 30 + 1).toString().padStart(2, '0')}",
            imageUrl = "https://picsum.photos/300/200?random=$index",
            url = "https://gizmodo.com/bitcoin-whales-are-offloading-their-bags-on-institutional-investors-2000623879"
        )
    }

    val gridItems = dummyNewsList.mapIndexed { index, news ->
        if (index % 5 == 0) {
            NewsContentType.Row(news)
        } else {
            NewsContentType.Grid(news)
        }
    }

    return gridItems
}