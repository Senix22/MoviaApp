package com.example.moviaapp.data.movie.details

import com.example.moviaapp.data.api.MovieApi
import com.example.moviaapp.data.models.MovieDetails
import com.example.moviaapp.data.models.MovieDetailsResult
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    val api: MovieApi,
    private val detailsMapper: MovieDetailsMapper
) {
    suspend fun getMovieDetails(movieId: String): MovieDetails =
        detailsMapper.mapMovieDetails(api.getMovieDetails(movieId))
}