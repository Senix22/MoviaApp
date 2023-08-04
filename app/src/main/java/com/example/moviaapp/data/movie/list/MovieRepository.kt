package com.example.moviaapp.data.movie.list

import com.example.moviaapp.data.models.MovieEntity
import com.example.moviaapp.data.models.UsualMovieResult
import com.example.moviaapp.data.api.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val moviesApi: MovieApi,
    private val moviesMapper: MovieResponseMapper
) {
    suspend fun requestMovies(
        page: Int? = null
    ): UsualMovieResult =
        moviesMapper.mapMovieResponse(
            moviesApi.getMovieList(
                page = page,
            )
        )

    suspend fun searchMovie(
        movie: String,
    ): Flow<List<MovieEntity>> =
        flow {
            emit(
                moviesMapper.mapSearchResponse(
                    moviesApi.searchMovie(
                        query = movie,
                    )
                )
            )
        }

}