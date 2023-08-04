package com.example.moviaapp.data.models

import com.google.gson.annotations.SerializedName

typealias MovieDetails = MovieDetailsResult<MovieDetailEntity>

sealed class MovieDetailsResult<out T> {
    data class Success<T>(
        val result: MovieDetailEntity
    ) : MovieDetailsResult<T>()

    data class Failure<T>(
        val errorText: String,
        val code: Int?
    ) : MovieDetailsResult<T>()
}


data class MovieDetailEntity(
    val adult: Boolean?,
    val backdropPath: String?,
    val belongsToCollection: List<BelongToCollectionEntity>?,
    val budget: Int?,
    val genres: List<MovieGenresEntity>?,
    val homepage: String?,
    val id: Int?,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Float?,
    val posterPath: String?,
    val productionCompanies: List<MovieProductionCompanyEntity>?,
    val productionCountries: List<MovieProductionCountriesEntity>?,
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguagesEntity>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Float?,
    val voteCount: Int?,
)


data class MovieGenresEntity(
    val id: Int?,
    val name: String?,
)

data class MovieProductionCompanyEntity(
    val id: Int?,
    val logoPath: String?,
    val name: String?,
    val originCountry: String?,
)

data class MovieProductionCountriesEntity(
    val iso: String?,
    val name: String?,
)

data class BelongToCollectionEntity(
    val id: Int?,
    val name: String?,
    val posterPath: String?,
    val backdropPath: String?,
)

data class SpokenLanguagesEntity(
    val englishName: String?,
    val iso: String?,
    val name: String?,
)