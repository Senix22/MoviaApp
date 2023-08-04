package com.example.moviaapp.data.movie.trailer

import com.example.moviaapp.data.models.TrailerResult
import com.example.moviaapp.data.api.MovieApi
import javax.inject.Inject

class TrailerRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val mapper: TrailerMapper
) {


    suspend fun getMovieId(movieId: String): TrailerResult =
        mapper.trailerMapper(movieApi.getTrailer(movieId = movieId))

}