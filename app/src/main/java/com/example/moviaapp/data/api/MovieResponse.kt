package com.example.moviaapp.data.api

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val pages: Int,
    @SerializedName("results") val movies: List<ResultResponse>?
    )

data class ResultResponse(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val rating: Float?,
    @SerializedName("release_date") val releaseDate: String?
)
