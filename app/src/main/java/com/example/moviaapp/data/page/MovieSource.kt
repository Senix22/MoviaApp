package com.example.moviaapp.data.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviaapp.common.Taggable
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.data.MovieResult
import com.example.moviaapp.data.movie.MovieRepository
import javax.inject.Inject

class MovieSource @Inject constructor(
    private val movieRepository: MovieRepository
) : PagingSource<Int, MovieEntity>(), Taggable {
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int {
        return INITIAL_REFRESH_KEY
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        return try {
            val nextPage = params.key ?: 1
            val movieResult = movieRepository.requestMovies(nextPage)

            if (movieResult is MovieResult.Success) {
                LoadResult.Page(
                    data = movieResult.result ?: emptyList(),
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage.plus(1),
                )
            } else {
                LoadResult.Error(IllegalStateException((movieResult as MovieResult.Failure).errorText))
            }
        } catch (e: Exception) {
            Log.e(tag, e.message, e)

            LoadResult.Error(e)
        }

    }

    private companion object {
        const val INITIAL_REFRESH_KEY = 1
    }


}