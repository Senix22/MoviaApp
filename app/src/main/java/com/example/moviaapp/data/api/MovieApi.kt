package com.example.moviaapp.data.api

import com.example.moviaapp.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @Headers("user-key: e64171211a64c2252b5e7368c3fdb57e")
    @GET("/3/account/{account_id}")
    suspend fun getDetails(
        @Query("page") next: Int?,
        @Query("session_id") sessionId: String,
    ): NetworkResponse<MovieResponse, Any>

    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String? = "e64171211a64c2252b5e7368c3fdb57e",
        @Query("page") page: Int?
    ): NetworkResponse<MovieResponse, Any>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieItem(

        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String? = "e64171211a64c2252b5e7368c3fdb57e",
    ): NetworkResponse<MovieItemResponse, Any>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String? = "e64171211a64c2252b5e7368c3fdb57e",
        @Query("query") query: String,
    ): NetworkResponse<MovieResponse, Any>
}
