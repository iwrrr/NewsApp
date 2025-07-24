package com.example.newsapp.presentation.home.viewed

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

sealed interface ViewedState {
    data object Initial : ViewedState
    data object Loading : ViewedState
    data object Empty : ViewedState
    data class Success(val data: List<News>) : ViewedState
    data class Error(val message: String) : ViewedState
}

class ViewedViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ViewedState>(ViewedState.Initial)
    val state = _state.asStateFlow()

    init {
        getViewedNews()
    }

    private fun getViewedNews() {
        viewModelScope.launch {
            _state.update { ViewedState.Loading }
            repository.getViewedNews().collectLatest { result ->
                when (result) {
                    is DataState.Success -> {
                        if (result.data.isEmpty()) {
                            _state.update { ViewedState.Empty }
                            return@collectLatest
                        }
                        _state.update { ViewedState.Success(result.data) }
                    }

                    is DataState.Error -> {
                        _state.update { ViewedState.Error(result.exception.localizedMessage.orEmpty()) }
                    }
                }
            }
        }
    }

    fun deleteNews(news: News) {
        viewModelScope.launch {
            repository.deleteNews(news)
        }
    }
}