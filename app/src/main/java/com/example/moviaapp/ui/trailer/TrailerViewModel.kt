package com.example.moviaapp.ui.trailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviaapp.common.State
import com.example.moviaapp.data.models.TrailerResult
import com.example.moviaapp.data.movie.trailer.TrailerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailerViewModel @Inject constructor(
    private val movieItem: TrailerRepository
) : ViewModel() {


    private val mutableStateFlow: MutableStateFlow<State<TrailerResult, Any>> =
        MutableStateFlow(State.Loading)
    var stateFlow: StateFlow<State<TrailerResult, Any>> = mutableStateFlow.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableStateFlow.value = State.Error(Any())
    }

    fun getMovieItem(id: String) {
        viewModelScope.launch(exceptionHandler) {
            mutableStateFlow.value = State.Content(movieItem.getMovieId((id)))
        }
    }
}