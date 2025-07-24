package com.example.newsapp.infrastructure.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.infrastructure.persistence.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = true)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}