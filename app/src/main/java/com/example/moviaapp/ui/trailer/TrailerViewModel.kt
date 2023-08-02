package com.example.moviaapp.ui.trailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviaapp.common.State
import com.example.moviaapp.data.MovieItem
import com.example.moviaapp.data.MovieItemResult
import com.example.moviaapp.data.movie.TrailerRepository
import com.example.moviaapp.di.DispatcherIo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.A
import javax.inject.Inject

@HiltViewModel
class TrailerViewModel @Inject constructor(
    private val movieItem: TrailerRepository
) : ViewModel() {


    private val mutableStateFlow: MutableStateFlow<State<MovieItem, Any>> =
        MutableStateFlow(State.Loading)
    var stateFlow: StateFlow<State<MovieItem, Any>> = mutableStateFlow.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableStateFlow.value = State.Error(Any())
    }

    fun getMovieItem(id: String) {
        viewModelScope.launch(exceptionHandler) {
            mutableStateFlow.value = State.Content(movieItem.getMovieId((id)))
        }
    }
}