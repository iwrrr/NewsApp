package com.example.newsapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import com.example.newsapp.presentation.home.headlines.HeadlinesScreen
import com.example.newsapp.presentation.home.viewed.ViewedScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClicked: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = remember { listOf("Top Headlines", "Viewed") }

    val headlinesNewsScrollState = rememberLazyGridState()
    val viewedNewsScrollState = rememberLazyListState()

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
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            TabRow(selectedTab) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(tab) })
                }
            }

            when (selectedTab) {
                0 -> HeadlinesScreen(
                    onNewsClick = {
                        uriHandler.openUri(it.url)
                    },
                    scrollState = headlinesNewsScrollState,
                )

                1 -> ViewedScreen(
                    onNewsClick = {
                        uriHandler.openUri(it.url)
                    },
                    scrollState = viewedNewsScrollState,
                )
            }
        }
    }
}