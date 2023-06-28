package com.example.moviaapp.data.movie

import android.util.Log
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.data.NewsResult
import com.example.moviaapp.data.UsualNewsResult
import com.example.moviaapp.data.api.MovieResponse
import com.example.moviaapp.network.NetworkResponse
import javax.inject.Inject

class MovieResponseMapper @Inject constructor() {

    fun mapMovieResponse(resultResponse: NetworkResponse<MovieResponse, Any>): UsualNewsResult {
        Log.d("Movie", "getMovie: asdonse.body}")
        return when (resultResponse) {
            is NetworkResponse.Success -> {
                Log.d("Movie", "getMovie: ${resultResponse.body}")
                with(resultResponse.body) {

                    NewsResult.Success(
                        page = page,
                        pages = pages,
                        result = movies?.mapNotNull {
                            MovieEntity(
                                title = it.title ?: return@mapNotNull null,
                                id = it.id ?: return@mapNotNull null,
                                overview = it.overview ?: return@mapNotNull null,
                                posterPath = it.posterPath ?: return@mapNotNull null,
                                backdropPath = it.backdropPath ?: return@mapNotNull null,
                                rating = it.rating ?: return@mapNotNull null,
                                releaseDate = it.releaseDate ?: return@mapNotNull null
                            )
                        }
                    )

                }

            }
            is NetworkResponse.NetworkError -> NewsResult.Failure(
                resultResponse.error.message.orEmpty(),
                null
            )
            is NetworkResponse.ApiError -> (
                    NewsResult.Failure(
                        resultResponse.body.toString(),
                        resultResponse.code
                    )
                    )
            is NetworkResponse.UnknownError -> (NewsResult.Failure(
                resultResponse.error.message.orEmpty(),
                null
            ))
        }
    }
}