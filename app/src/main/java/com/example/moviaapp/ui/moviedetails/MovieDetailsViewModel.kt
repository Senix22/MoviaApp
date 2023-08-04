package com.example.moviaapp.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviaapp.common.State
import com.example.moviaapp.data.models.MovieDetails
import com.example.moviaapp.data.movie.details.MovieDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    val repository: MovieDetailsRepository
) : ViewModel() {
    private val mutableStateFlow: MutableStateFlow<State<MovieDetails, Any>> =
        MutableStateFlow(State.Loading)
    var stateFlow: StateFlow<State<MovieDetails, Any>> = mutableStateFlow.asStateFlow()

    init {

    }

    fun getDetails(movieId: String) {
        viewModelScope.launch {
            mutableStateFlow.value = State.Content(repository.getMovieDetails(movieId))
        }
    }
}