package com.example.moviaapp.data.movie

import com.example.moviaapp.data.UsualNewsResult
import com.example.moviaapp.data.api.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val newsApi: MovieApi,
    private val newsMapper: MovieResponseMapper
) {
    suspend fun requestNews(
        page: Int? = null
    ): UsualNewsResult =

            newsMapper.mapMovieResponse(
                newsApi.getMovieList(
                    page = page,
                )
            )


}