package com.example.newsapp.infrastructure.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("code")
    val code: String?,
    @SerialName("message")
    val message: String?,
    @SerialName("status")
    val status: String?
)