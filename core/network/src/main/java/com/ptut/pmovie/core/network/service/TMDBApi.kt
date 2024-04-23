package com.ptut.pmovie.core.network.service

import arrow.core.Either
import com.ptut.pmovie.core.model.MoviesResponse
import com.ptut.pmovie.core.model.TvSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
    ): Either<Throwable, MoviesResponse>

    @GET("movie/{movie_category}")
    suspend fun getMovies(
        @Path("movie_category") movieCategory: String,
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
    ): Either<Throwable, MoviesResponse>

    @GET("trending/tv/day")
    suspend fun getTrendingTvSeries(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
    ): Either<Throwable, TvSeriesResponse>

    @GET("tv/{tv_category}")
    suspend fun getTvSeries(
        @Path("tv_category") tvCategory: String,
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
    ): Either<Throwable, TvSeriesResponse>

    companion object {
        const val STARTING_PAGE_INDEX = 0
        const val API_KEY = ""
    }
}
