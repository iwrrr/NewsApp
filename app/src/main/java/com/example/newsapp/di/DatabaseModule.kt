package com.example.newsapp.di

import androidx.room.Room
import com.example.newsapp.infrastructure.persistence.NewsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            "news_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<NewsDatabase>().newsDao() }
} 