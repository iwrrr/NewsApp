package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatIsoDate(input: String): String {
    return try {
        // Format input ISO 8601
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC") // karena Z = UTC

        val date = isoFormat.parse(input)

        // Format output, misal: 23 Jul 2025 08:27
        val outputFormat = SimpleDateFormat("E, dd MMMM yyyy HH.mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault() // lokal timezone

        outputFormat.format(date!!)
    } catch (e: Exception) {
        "-"
    }
}