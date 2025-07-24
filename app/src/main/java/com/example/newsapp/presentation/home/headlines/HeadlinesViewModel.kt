package com.example.newsapp.presentation.home.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.presentation.home.utils.NewsContentType
import com.example.newsapp.utils.state.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HeadlinesState {
    data object Initial : HeadlinesState
    data object Loading : HeadlinesState
    data object Empty : HeadlinesState
    data class Success(val data: List<NewsContentType>) : HeadlinesState
    data class Error(val message: String) : HeadlinesState
}

class HeadlinesViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HeadlinesState>(HeadlinesState.Initial)
    val state = _state.asStateFlow()

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            _state.update { HeadlinesState.Loading }
            repository.getNews().collectLatest { result ->
                when (result) {
                    is DataState.Success -> {
                        if (result.data.isEmpty()) {
                            _state.update { HeadlinesState.Empty }
                            return@collectLatest
                        }

                        val gridItems = result.data.mapIndexed { index, news ->
                            if (index % 5 == 0) {
                                NewsContentType.Row(news)
                            } else {
                                NewsContentType.Grid(news)
                            }
                        }
                        _state.update { HeadlinesState.Success(gridItems) }
                    }

                    is DataState.Error -> {
                        _state.update { HeadlinesState.Error(result.exception.localizedMessage.orEmpty()) }
                    }
                }
            }
        }
    }

    fun saveNews(news: News) {
        viewModelScope.launch {
            repository.saveNews(news)
        }
    }
}