package com.example.moviaapp.common

import android.view.View
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.data.MovieItemEntity
import com.example.moviaapp.data.api.MovieItemResultResponse
import com.example.moviaapp.data.api.ResultResponse

fun ResultResponse.asMovie() = MovieEntity(
    id = id ?: 0,
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    rating = rating ?: 0f,
    releaseDate = releaseDate.orEmpty()

)

fun MovieItemResultResponse.asMovieItem() = MovieItemEntity(
    iso = iso.orEmpty(),
    iso2 = iso2.orEmpty(),
    name = name.orEmpty(),
    key = key.orEmpty(),
    site = site.orEmpty(),
    size = size ?: 0,
    type = type.orEmpty(),
    official = official ?: false,
    publishedAt = publishedAt.orEmpty(),
    id = id.orEmpty()
)

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}


fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}