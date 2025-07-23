package com.example.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.domain.model.News

@Composable
fun NewsItem(
    news: News,
    color: Color = Color.Red,
    onClick: (news: News) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick(news) },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = news.title,
            color = Color.White,
        )
    }
}
