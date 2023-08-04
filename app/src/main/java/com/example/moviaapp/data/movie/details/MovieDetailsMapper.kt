package com.example.moviaapp.data.movie.details

import com.example.moviaapp.data.api.MovieDetailResponse
import com.example.moviaapp.data.api.MovieGenres
import com.example.moviaapp.data.api.MovieProductionCompany
import com.example.moviaapp.data.api.MovieProductionCountries
import com.example.moviaapp.data.api.SpokenLanguages
import com.example.moviaapp.data.models.MovieDetailEntity
import com.example.moviaapp.data.models.MovieDetails
import com.example.moviaapp.data.models.MovieDetailsResult
import com.example.moviaapp.data.models.MovieGenresEntity
import com.example.moviaapp.data.models.MovieProductionCompanyEntity
import com.example.moviaapp.data.models.MovieProductionCountriesEntity
import com.example.moviaapp.data.models.MovieResult
import com.example.moviaapp.data.models.SpokenLanguagesEntity
import com.example.moviaapp.network.NetworkResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() {

    suspend fun mapMovieDetails(response: NetworkResponse<MovieDetailResponse, Any>): MovieDetails {
        return when (response) {
            is NetworkResponse.Success -> {
                MovieDetailsResult.Success(response.body.toDetails())
            }

            is NetworkResponse.NetworkError -> MovieDetailsResult.Failure(
                response.error.message.orEmpty(),
                null
            )

            is NetworkResponse.ApiError -> (
                    MovieDetailsResult.Failure(
                        response.body.toString(),
                        response.code
                    )
                    )

            is NetworkResponse.UnknownError -> (MovieDetailsResult.Failure(
                response.error.message.orEmpty(),
                null
            ))

        }
    }


    private suspend fun MovieDetailResponse.toDetails() = coroutineScope {
        val genres = genres?.map {
            async { it.toGenre() }
        }

        val productionCompany = productionCompanies?.map {
            async { it.toProductionCompany() }
        }

        val productionCountry = productionCountries?.map {
            async {
                it.toProductionCountry()
            }
        }
        val language = spokenLanguages?.map {
            async {
                it.toSpokenLanguage()
            }
        }
        MovieDetailEntity(
            adult = adult,
            backdropPath = backdropPath.orEmpty(),
            belongsToCollection = belongsToCollection.orEmpty(),
            budget = budget ?: 0,
            genres = genres?.awaitAll(),
            homepage = homepage.orEmpty(),
            id = id ?: 0,
            imdbId = imdbId.orEmpty(),
            originalLanguage = originalLanguage.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            overview = overview.orEmpty(),
            popularity = popularity ?: 0f,
            posterPath = posterPath.orEmpty(),
            productionCompanies = productionCompany?.awaitAll(),
            productionCountries = productionCountry?.awaitAll(),
            releaseDate = releaseDate.orEmpty(),
            revenue = revenue ?: 0,
            runtime = runtime ?: 0,
            spokenLanguages = language?.awaitAll(),
            status = status.orEmpty(),
            tagline = tagline.orEmpty(),
            title = title.orEmpty(),
            video = video,
            voteAverage = voteAverage ?: 0f,
            voteCount = voteCount ?: 0
        )
    }


    private fun MovieGenres.toGenre() =
        MovieGenresEntity(
            id = id ?: 0,
            name = name.orEmpty()
        )

    private fun MovieProductionCompany.toProductionCompany() =
        MovieProductionCompanyEntity(
            id = id ?: 0,
            logoPath = logoPath.orEmpty(),
            name = name.orEmpty(),
            originCountry = originCountry.orEmpty()
        )

    private fun MovieProductionCountries.toProductionCountry() =
        MovieProductionCountriesEntity(
            iso = iso.orEmpty(),
            name = name.orEmpty()
        )

    private fun SpokenLanguages.toSpokenLanguage() =
        SpokenLanguagesEntity(
            englishName = englishName.orEmpty(),
            iso = iso.orEmpty(),
            name = name.orEmpty()
        )
}