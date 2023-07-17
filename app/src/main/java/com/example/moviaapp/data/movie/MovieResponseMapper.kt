package com.example.moviaapp.data.movie

import com.example.moviaapp.common.asMovie
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.data.MovieResult

import com.example.moviaapp.data.UsualMovieResult
import com.example.moviaapp.data.api.MovieResponse
import com.example.moviaapp.network.NetworkResponse
import javax.inject.Inject

class MovieResponseMapper @Inject constructor() {

    fun mapMovieResponse(resultResponse: NetworkResponse<MovieResponse, Any>): UsualMovieResult {
        return when (resultResponse) {
            is NetworkResponse.Success -> {
                with(resultResponse.body) {
                    MovieResult.Success(
                        page = page,
                        pages = pages,
                        result = movies?.map {
                            it.asMovie()
                        }

                    )
                }
            }

            is NetworkResponse.NetworkError -> MovieResult.Failure(
                resultResponse.error.message.orEmpty(),
                null
            )

            is NetworkResponse.ApiError -> (
                    MovieResult.Failure(
                        resultResponse.body.toString(),
                        resultResponse.code
                    )
                    )

            is NetworkResponse.UnknownError -> (MovieResult.Failure(
                resultResponse.error.message.orEmpty(),
                null
            ))
        }
    }

    fun mapSearchResponse(resultResponse: NetworkResponse<MovieResponse, Any>): List<MovieEntity> {
        return when (resultResponse) {

            is NetworkResponse.Success -> {
                resultResponse.body.movies?.map {
                    it.asMovie()
                } ?: emptyList()
            }

            else -> {
                emptyList<MovieEntity>()
            }
        }
    }
}