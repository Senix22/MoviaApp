package com.example.moviaapp.data

typealias UsualNewsResult = NewsResult<MovieEntity>

sealed class NewsResult<out T> {
    data class Success<T>(
        val page: Int,
        val pages: Int,
        val result: List<T>?,
    ) : NewsResult<T>()

    data class Failure<T>(
        val errorText: String,
        val code: Int?
    ) : NewsResult<T>()
}

data class MovieEntity (
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val rating: Float,
    val releaseDate: String
)
