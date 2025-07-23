package com.example.newsapp.infrastructure.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer a9715c8d48974dfe9af24a03f59fb7e4")
        return chain.proceed(requestBuilder.build())
    }
}