package com.example.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newsapp.domain.model.News
import com.example.newsapp.utils.formatIsoDate

@Composable
fun RowNewsItem(
    modifier: Modifier = Modifier,
    news: News,
    onClick: (news: News) -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .height(IntrinsicSize.Min)
            .clickable { onClick(news) },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NetworkImage(
            modifier = Modifier
                .size(120.dp)
                .clip(MaterialTheme.shapes.medium),
            imageUrl = news.imageUrl,
            contentDescription = news.title,
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = news.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = news.author,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = formatIsoDate(news.publishedAt),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}