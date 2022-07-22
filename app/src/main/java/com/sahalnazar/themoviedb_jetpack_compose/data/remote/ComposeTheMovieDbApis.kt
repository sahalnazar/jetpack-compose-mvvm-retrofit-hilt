package com.sahalnazar.themoviedb_jetpack_compose.data.remote

import com.sahalnazar.themoviedb_jetpack_compose.data.model.MovieDetailResponse
import com.sahalnazar.themoviedb_jetpack_compose.data.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComposeTheMovieDbApis {

    @GET("discover/movie")
    suspend fun fetchMovieList(
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort_by: String = "popularity.asc",
        @Query("page") page: String,
    ): Response<MovieListResponse>

    @GET("movie/{movieId}")
    suspend fun fetMovieDetail(
        @Path(value = "movieId", encoded = true) movieId: String,
    ): Response<MovieDetailResponse>

}