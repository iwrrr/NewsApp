package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.networkModules
import com.example.newsapp.di.repositoryModules
import com.example.newsapp.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(networkModules, repositoryModules, viewModelModules)
        }
    }
}