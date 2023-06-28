package com.example.moviaapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.data.movie.MovieRepository
import com.example.moviaapp.data.page.MovieSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieSource: MovieSource
//    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<State<Any, Any>>(State.Loading)
    val state: Flow<State<Any, Any>> = _state.onEach { delay(500L) }


    init {
        getMovie()
    }
    private val _news = MutableStateFlow<PagingData<MovieEntity>?>(null)
    val news: Flow<PagingData<MovieEntity>> = _news
        .filterNotNull()
        .onEach {
            _state.value = State.Content(Any())
        }


    fun getMovie() {
        viewModelScope.launch {
            Pager(PagingConfig(NEWS_PAGE_SIZE)) { movieSource }.flow
                .cachedIn(this)
                .catch { e ->
//                    Log.e(tag, "getNews: $e")

                    _state.value = State.Error(Any())
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _news.emit(it)
                    Log.d("Movie", "getMovie: $it")
                }
        }
    }



    companion object {
        private const val NEWS_PAGE_SIZE = 100
    }
}