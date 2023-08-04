package com.example.moviaapp.data.movie.trailer

import com.example.moviaapp.common.asMovieItem
import com.example.moviaapp.data.models.TrailerResult
import com.example.moviaapp.data.models.MovieItemResult
import com.example.moviaapp.data.api.MovieItemResponse

import com.example.moviaapp.network.NetworkResponse
import javax.inject.Inject

class TrailerMapper @Inject constructor() {


    fun trailerMapper(movieResponse: NetworkResponse<MovieItemResponse, Any>): TrailerResult {

        return when (movieResponse) {
            is NetworkResponse.Success -> {
                with(movieResponse.body) {
                    MovieItemResult.Success(
                        result = results?.map {
                            it.asMovieItem()
                        } ?: emptyList()
                    )
                }
            }

            is NetworkResponse.ApiError -> {
                MovieItemResult.Failure(
                    movieResponse.body.toString(),
                    movieResponse.code
                )

            }

            is NetworkResponse.NetworkError -> MovieItemResult.Failure(
                movieResponse.error.message.orEmpty(),
                null
            )

            is NetworkResponse.UnknownError -> (MovieItemResult.Failure(
                movieResponse.error.message.orEmpty(),
                null
            ))
        }
    }

}