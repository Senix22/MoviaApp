package com.example.moviaapp.data.movie

import com.example.moviaapp.data.MovieItem
import com.example.moviaapp.data.api.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrailerRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val mapper: TrailerMapper
) {


    suspend fun getMovieId(movieId: String): Flow<MovieItem> =
        flow {
            emit(mapper.movieItemMapper(movieApi.getMovieItem(movieId = movieId)))
        }
}