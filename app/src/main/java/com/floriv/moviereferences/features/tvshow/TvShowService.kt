package com.floriv.moviereferences.features.tvshow

import com.floriv.moviereferences.features.tvshow.detail.DetailTvShow
import com.floriv.moviereferences.features.tvshow.list.ListTvShow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowService @Inject constructor(retrofit: Retrofit) : TvShowApi {

    private val api by lazy { retrofit.create(TvShowApi::class.java) }

    override fun searchTvShow(query: String?): Call<ListTvShow> {
        return api.searchTvShow(query)
    }

    override fun getDetailTvShow(id: String?): Call<DetailTvShow> {
        return api.getDetailTvShow(id)
    }

    override fun getTvShows(): Call<ListTvShow> {
        return api.getTvShows()
    }

}