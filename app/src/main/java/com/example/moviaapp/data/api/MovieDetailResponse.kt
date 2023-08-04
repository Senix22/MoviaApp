package com.example.moviaapp.data.api

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("genres") val genres: List<MovieGenres>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Float?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<MovieProductionCompany>?,
    @SerializedName("production_countries") val productionCountries: List<MovieProductionCountries>?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguages>?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("vote_count") val voteCount: Int?,
)


data class MovieGenres(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
)

data class MovieProductionCompany(
    @SerializedName("id") val id: Int?,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("origin_country") val originCountry: String?,
)

data class MovieProductionCountries(
    @SerializedName("iso_3166_1") val iso: String?,
    @SerializedName("name") val name: String?,
)

data class SpokenLanguages(
    @SerializedName("english_name") val englishName: String?,
    @SerializedName("iso_639_1") val iso: String?,
    @SerializedName("name") val name: String?,
)

data class BelongToCollection(
    @SerializedName("id")val id: Int?,
    @SerializedName("name")val name: String?,
    @SerializedName("poster_path")val posterPath: String?,
    @SerializedName("backdrop_path")val backdropPath: String?,
)