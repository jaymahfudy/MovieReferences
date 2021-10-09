package com.floriv.moviereferences.features.trending

import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingService @Inject constructor(retrofit: Retrofit) : TrendingApi {

    private val api by lazy { retrofit.create(TrendingApi::class.java) }

    override fun getTrending(): Call<Trending> {
        return api.getTrending()
    }

}