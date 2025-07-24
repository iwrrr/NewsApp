package com.example.newsapp.di

import com.example.newsapp.presentation.home.headlines.HeadlinesViewModel
import com.example.newsapp.presentation.home.viewed.ViewedViewModel
import com.example.newsapp.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
    viewModelOf(::HeadlinesViewModel)
    viewModelOf(::ViewedViewModel)
    viewModelOf(::SearchViewModel)
}