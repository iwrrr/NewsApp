package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.presentation.home.utils.NewsContentType
import com.example.newsapp.utils.state.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HomeState {
    data object Initial : HomeState
    data object Loading : HomeState
    data object Empty : HomeState
    data class Success(val data: List<NewsContentType>) : HomeState
    data class Error(val message: String) : HomeState
}

class HomeViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Initial)
    val state = _state.asStateFlow()

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            _state.update { HomeState.Loading }
            repository.getNews().collectLatest { result ->
                when (result) {
                    is DataState.Success -> {
                        if (result.data.isEmpty()) {
                            _state.update { HomeState.Empty }
                            return@collectLatest
                        }

                        val gridItems = result.data.mapIndexed { index, news ->
                            if (index % 5 == 0) {
                                NewsContentType.Row(news)
                            } else {
                                NewsContentType.Grid(news)
                            }
                        }
                        _state.update { HomeState.Success(gridItems) }
                    }

                    is DataState.Error -> {
                        _state.update { HomeState.Error(result.exception.localizedMessage.orEmpty()) }
                    }
                }
            }
        }
    }
}