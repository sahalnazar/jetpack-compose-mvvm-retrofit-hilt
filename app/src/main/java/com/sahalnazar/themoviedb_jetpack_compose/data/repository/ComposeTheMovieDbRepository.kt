package com.sahalnazar.themoviedb_jetpack_compose.data.repository

import com.sahalnazar.themoviedb_jetpack_compose.data.remote.ComposeTheMovieDbApis
import com.sahalnazar.themoviedb_jetpack_compose.util.safeApiCall
import javax.inject.Inject

class ComposeTheMovieDbRepository @Inject constructor(
    private val composeTheMovieDbApis: ComposeTheMovieDbApis
) {

    suspend fun fetchMovieList(
        page: String
    ) = safeApiCall {
        composeTheMovieDbApis.fetchMovieList(
            page = page
        )
    }

    suspend fun fetMovieDetail(
        movieId: String
    ) = safeApiCall {
        composeTheMovieDbApis.fetMovieDetail(
            movieId = movieId
        )
    }

}