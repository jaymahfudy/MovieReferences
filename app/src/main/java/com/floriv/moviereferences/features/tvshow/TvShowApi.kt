package com.floriv.moviereferences.features.tvshow

import com.floriv.moviereferences.BuildConfig
import com.floriv.moviereferences.features.tvshow.detail.DetailTvShow
import com.floriv.moviereferences.features.tvshow.list.ListTvShow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface TvShowApi {
    @GET("search/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    fun searchTvShow(@Query("query") query: String?): Call<ListTvShow>

    @GET("tv/{id}?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    fun getDetailTvShow(@Path("id") id: String?): Call<DetailTvShow>

    @GET("discover/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    fun getTvShows(): Call<ListTvShow>
}