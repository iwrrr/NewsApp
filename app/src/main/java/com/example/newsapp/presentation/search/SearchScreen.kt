package com.example.newsapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.newsapp.domain.model.News
import com.example.newsapp.ui.components.CollapsedAppBar
import com.example.newsapp.ui.components.SearchBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackPressed: () -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val newsList = viewModel.newsList.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val uriHandler = LocalUriHandler.current

    val expandedAppBarHeight = 80.dp

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = scrollBehavior.state
    val appBarExpanded by remember {
        // App bar expanded state
        // CollapsedFraction 1f = collapsed
        // CollapsedFraction 0f = expanded
        derivedStateOf { scrollState.collapsedFraction < 0.7f }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .zIndex(0f)
                    .fillMaxWidth()
            ) {
                CollapsedAppBar(
                    visible = !appBarExpanded,
                    onBackPressed = onBackPressed
                )
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Discover",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = "News from all around the world",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    expandedHeight = expandedAppBarHeight,
                    windowInsets = WindowInsets(0),
                    scrollBehavior = scrollBehavior
                )
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.updateSearchQuery(it) }
                )
            }
        },
    ) { innerPadding ->
        when {
            searchQuery.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Please enter a search keyword")
                }
            }

            newsList.loadState.refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            newsList.loadState.refresh is LoadState.NotLoading && newsList.loadState.append.endOfPaginationReached && newsList.itemCount == 0 -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "News not found")
                }
            }

            newsList.loadState.refresh is LoadState.Error && newsList.itemCount == 0 -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${(newsList.loadState.refresh as LoadState.Error).error}")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        count = newsList.itemCount,
                        key = newsList.itemKey { it.id }
                    ) { index ->
                        val news = newsList[index]
                        news?.let {
                            SearchItem(
                                news = news,
                                onClick = {
                                    uriHandler.openUri(news.url)
                                },
                            )
                        }
                    }

                    // Handle append loading/error states
                    when (newsList.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    news: News,
    onClick: (news: News) -> Unit
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clickable { onClick(news) },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = news.source,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = news.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${news.author} - ${news.publishedAt}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}