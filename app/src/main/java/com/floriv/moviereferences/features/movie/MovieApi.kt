package com.floriv.moviereferences.features.movie

import com.floriv.moviereferences.BuildConfig
import com.floriv.moviereferences.features.movie.detail.DetailMovie
import com.floriv.moviereferences.features.movie.list.ListMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieApi {
    @GET("search/movie?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun searchMovie(@Query("query") query: String?): Call<ListMovie>

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getDetailMovie(@Path("id") id: String?): Call<DetailMovie>

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getMovies(): Call<ListMovie>

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getTodayMovies(
        @Query("primary_release_date.gte") startDate: String?,
        @Query("primary_release_date.lte") endDate: String?
    ): Call<ListMovie>
}