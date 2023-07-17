package com.example.moviaapp.data

typealias UsualMovieResult = MovieResult<MovieEntity>
typealias MovieItem = MovieItemResult<MovieItemEntity>

sealed class MovieResult<out T> {
    data class Success<T>(
        val page: Int,
        val pages: Int,
        val result: List<T>?,
    ) : MovieResult<T>()

    data class Failure<T>(
        val errorText: String,
        val code: Int?
    ) : MovieResult<T>()
}

data class MovieEntity(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val rating: Float,
    val releaseDate: String
)


sealed class MovieItemResult<out T> {
    data class Success<T>(
        val result: List<MovieItemEntity>
    ) : MovieItemResult<T>()

    data class Failure<T>(
        val errorText: String,
        val code: Int?
    ) : MovieItemResult<T>()
}

data class MovieItemEntity(
    val iso: String,
    val iso2: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
    val id: String,
)
