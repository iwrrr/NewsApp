package com.example.newsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter

@Composable
fun NetworkImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    fallbackSize: Modifier = Modifier.size(40.dp)
) {
    val painter = rememberAsyncImagePainter(imageUrl)
    val state by painter.state.collectAsStateWithLifecycle()

    when (state) {
        is AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                modifier = modifier,
                contentScale = ContentScale.Crop,
                painter = painter,
                contentDescription = contentDescription,
            )
        }

        is AsyncImagePainter.State.Error -> {
            val errorPainter = rememberAsyncImagePainter("https://archive.org/download/placeholder-image/placeholder-image.jpg")
            Image(
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = errorPainter,
                contentDescription = contentDescription,
            )
        }
    }
}