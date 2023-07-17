package com.example.moviaapp.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviaapp.common.State
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.data.MovieItem
import com.example.moviaapp.data.movie.MovieRepository

import com.example.moviaapp.data.page.MovieSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieSource: MovieSource,
    private val movie: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<State<Any, Any>>(State.Loading)
    var state: Flow<State<Any, Any>> = _state.onEach { delay(500L) }


    init {
        getMovie()
    }

    private val _movieList = MutableStateFlow<PagingData<MovieEntity>?>(null)
    val movieList: Flow<PagingData<MovieEntity>> = _movieList
        .filterNotNull()
        .onEach {
            _state.value = State.Content(Any())
        }
    private val _searchList: MutableStateFlow<List<MovieEntity>> =
        MutableStateFlow(ArrayList())
    var searchList: StateFlow<List<MovieEntity>> = _searchList


    private fun getMovie() {
        viewModelScope.launch {
            Pager(PagingConfig(MOVIE_PAGE_SIZE)) { movieSource }.flow
                .cachedIn(this)
                .catch { e ->

                    _state.value = State.Error(Any())
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _movieList.emit(it)
                }
        }
    }

    fun searchMovie(id: String) {
        viewModelScope.launch {
            movie.searchMovie(id)
                .flowOn(Dispatchers.IO)
                .map { it }
                .collect {
                    _searchList.emit(it)
                }
        }
    }


    companion object {
        private const val MOVIE_PAGE_SIZE = 5
    }
}