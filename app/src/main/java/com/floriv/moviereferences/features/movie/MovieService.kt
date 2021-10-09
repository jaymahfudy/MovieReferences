package com.floriv.moviereferences.features.movie

import com.floriv.moviereferences.features.movie.detail.DetailMovie
import com.floriv.moviereferences.features.movie.list.ListMovie
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieService @Inject constructor(retrofit: Retrofit) : MovieApi {

    private val api by lazy { retrofit.create(MovieApi::class.java) }

    override fun searchMovie(query: String?): Call<ListMovie> {
        return api.searchMovie(query)
    }

    override fun getDetailMovie(id: String?): Call<DetailMovie> {
        return api.getDetailMovie(id)
    }

    override fun getMovies(): Call<ListMovie> {
        return api.getMovies()
    }

    override fun getTodayMovies(startDate: String?, endDate: String?): Call<ListMovie> {
        return api.getTodayMovies(startDate, endDate)
    }

}