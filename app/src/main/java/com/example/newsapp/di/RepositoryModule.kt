package com.example.newsapp.di

import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.infrastructure.repository.NewsRepositoryImpl
import org.koin.dsl.module

val repositoryModules = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            apiService = get(),
        )
    }
}