package com.example.newsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.components.NewsItem
import com.example.newsapp.ui.utils.NewsContentType
import com.example.newsapp.ui.utils.generateDummyGridItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClicked: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val items = remember {
        generateDummyGridItems()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "News App",
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSearchClicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = items,
                span = { item ->
                    when (item) {
                        is NewsContentType.Row -> GridItemSpan(2) // full row
                        is NewsContentType.Grid -> GridItemSpan(1) // half width
                    }
                },
                key = { item ->
                    when (item) {
                        is NewsContentType.Row -> "image-${item.news.id}"
                        is NewsContentType.Grid -> "text-${item.news.id}"
                    }
                }
            ) { item ->
                when (item) {
                    is NewsContentType.Row -> {
                        NewsItem(
                            news = item.news,
                            onClick = {
                                uriHandler.openUri(item.news.url)
                            },
                        )
                    }

                    is NewsContentType.Grid -> {
                        NewsItem(
                            news = item.news,
                            color = Color.DarkGray,
                            onClick = {
                                uriHandler.openUri(item.news.url)
                            }
                        )
                    }
                }
            }
        }
    }
}