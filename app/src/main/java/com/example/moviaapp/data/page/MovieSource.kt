package com.example.moviaapp.data.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviaapp.common.Taggable
import com.example.moviaapp.data.models.MovieEntity
import com.example.moviaapp.data.models.MovieResult
import com.example.moviaapp.data.movie.list.MovieRepository
import javax.inject.Inject

class MovieSource @Inject constructor(
    private val movieRepository: MovieRepository
) : PagingSource<Int, MovieEntity>(), Taggable {
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int {
        return INITIAL_REFRESH_KEY
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        return try {
            val nextPage = params.key ?: INITIAL_REFRESH_KEY
            val movieResult = movieRepository.requestMovies(nextPage)

            if (movieResult is MovieResult.Success) {
                LoadResult.Page(
                    data = movieResult.result ?: emptyList(),
                    prevKey = if (nextPage == INITIAL_REFRESH_KEY) null else nextPage - NEXT_PAGE,
                    nextKey = nextPage.plus(NEXT_PAGE),
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
        const val NEXT_PAGE = 1
    }


}