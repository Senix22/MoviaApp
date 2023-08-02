package com.example.moviaapp.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviaapp.common.State
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.data.movie.MovieRepository

import com.example.moviaapp.data.page.MovieSource
import com.example.moviaapp.di.DispatcherIo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    @DispatcherIo private val workDispatcher: CoroutineDispatcher,
    private val movieSource: MovieSource,
    private val movie: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<State<Any, Any>>(State.Loading)
    var state: Flow<State<Any, Any>> = _state
    private val _movieList = MutableStateFlow<PagingData<MovieEntity>?>(null)
    val movieList: Flow<PagingData<MovieEntity>> = _movieList
        .filterNotNull()


    init {
        getMovie()
    }


    private fun getMovie() {
        viewModelScope.launch {
            Pager(PagingConfig(MOVIE_PAGE_SIZE)) { movieSource }.flow
                .cachedIn(this)
                .onEach { _state.value = State.Content(it) }
                .catch { e ->
                    _state.value = State.Error(Any())
                }
                .flowOn(workDispatcher)
                .collect {
                    _movieList.emit(it)
                }
        }
    }

    fun searchMovie(string: String) {
        viewModelScope.launch {
            if (string.isEmpty()) {
                getMovie()
            }
            else {
                movie.searchMovie(string)
                    .flowOn(workDispatcher)
                    .map { PagingData.from(it) }
                    .collect {
                        _movieList.emit(it)
                    }
            }
        }
    }


    companion object {
        private const val MOVIE_PAGE_SIZE = 5
    }
}