package com.example.newsapp.di

import com.example.newsapp.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
    viewModelOf(::HomeViewModel)
}