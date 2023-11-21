package com.example.bbapp

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String): TmdbMovieResult

    @GET("movie/{id}")
    suspend fun detailmovie( @Path("id") id: String, @Query("api_key") api_key: String): TmdbMovieDetail
}