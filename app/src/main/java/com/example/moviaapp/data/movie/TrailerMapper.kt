package com.example.moviaapp.data.movie

import com.example.moviaapp.common.asMovieItem
import com.example.moviaapp.data.MovieItem
import com.example.moviaapp.data.MovieItemResult
import com.example.moviaapp.data.api.MovieItemResponse

import com.example.moviaapp.network.NetworkResponse
import javax.inject.Inject

class TrailerMapper @Inject constructor() {


    fun movieItemMapper(movieResponse: NetworkResponse<MovieItemResponse, Any>): MovieItem {

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