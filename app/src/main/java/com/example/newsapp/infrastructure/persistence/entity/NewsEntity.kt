package com.example.newsapp.infrastructure.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val author: String,
    @SerialName("published_at")
    val publishedAt: String,
    @SerialName("image_url")
    val imageUrl: String,
    val url: String,
    val source: String,
    @SerialName("created_at")
    val createdAt: Long = System.currentTimeMillis(),
)