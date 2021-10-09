package com.floriv.moviereferences.features.trending

import com.floriv.moviereferences.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface TrendingApi {
    @GET("trending/person/day?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    fun getTrending(): Call<Trending>
}