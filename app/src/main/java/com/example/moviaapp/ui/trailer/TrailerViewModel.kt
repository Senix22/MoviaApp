package com.example.moviaapp.ui.trailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviaapp.common.State
import com.example.moviaapp.data.MovieItem
import com.example.moviaapp.data.movie.TrailerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailerViewModel @Inject constructor(
    private val movieItem: TrailerRepository
) : ViewModel() {



    private val mutableStateFlow: MutableStateFlow<State<MovieItem,Nothing>> =
        MutableStateFlow(State.Loading)
    var stateFlow: StateFlow<State<MovieItem,Nothing>> = mutableStateFlow.asStateFlow()

    fun getMovieItem(id: String) {
        viewModelScope.launch {
            movieItem.getMovieId(id)
                .map {
                    it
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    mutableStateFlow.value = State.Content(it)
                }

        }
    }
}