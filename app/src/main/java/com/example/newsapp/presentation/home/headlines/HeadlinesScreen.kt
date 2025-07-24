package com.example.newsapp.presentation.home.headlines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.domain.model.News
import com.example.newsapp.presentation.home.utils.NewsContentType
import com.example.newsapp.presentation.home.utils.NewsShimmerContentType
import com.example.newsapp.ui.components.NewsItem
import com.example.newsapp.ui.components.NewsItemShimmer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HeadlinesScreen(
    modifier: Modifier = Modifier,
    onNewsClick: (news: News) -> Unit,
    scrollState: LazyGridState,
    viewModel: HeadlinesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val result = state) {
        HeadlinesState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Empty")
            }
        }

        HeadlinesState.Loading -> {
            val items = remember {
                (1..10).mapIndexed { index, item ->
                    if (index % 5 == 0) {
                        NewsShimmerContentType.Row(item)
                    } else {
                        NewsShimmerContentType.Grid(item)
                    }
                }
            }

            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = items,
                    span = { item ->
                        when (item) {
                            is NewsShimmerContentType.Row -> GridItemSpan(2) // full row
                            is NewsShimmerContentType.Grid -> GridItemSpan(1) // half width
                        }
                    },
                ) { item ->
                    when (item) {
                        is NewsShimmerContentType.Row -> {
                            NewsItemShimmer()
                        }

                        is NewsShimmerContentType.Grid -> {
                            NewsItemShimmer()
                        }
                    }
                }
            }
        }

        is HeadlinesState.Success -> {
            LazyVerticalGrid(
                modifier = modifier,
                state = scrollState,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = result.data,
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
                                    onNewsClick(item.news)
                                    viewModel.saveNews(it)
                                },
                            )
                        }

                        is NewsContentType.Grid -> {
                            NewsItem(
                                news = item.news,
                                onClick = {
                                    onNewsClick(item.news)
                                    viewModel.saveNews(it)
                                }
                            )
                        }
                    }
                }
            }
        }

        is HeadlinesState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${result.message}")
            }
        }

        else -> {}
    }
}