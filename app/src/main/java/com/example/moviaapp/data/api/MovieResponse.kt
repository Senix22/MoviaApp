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



data class MovieItemResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("results") val results : List<MovieItemResultResponse>?
)

data class MovieItemResultResponse(
    @SerializedName("iso_639_1") val iso : String?,
    @SerializedName("iso_3166_1") val iso2 : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("key") val key : String?,
    @SerializedName("site") val site : String?,
    @SerializedName("size") val size : Int?,
    @SerializedName("type") val type : String?,
    @SerializedName("official") val official : Boolean?,
    @SerializedName("published_at") val publishedAt : String?,
    @SerializedName("id") val id : String?,
)