package com.floriv.moviereferences.features.tvshow.list

import com.google.gson.annotations.SerializedName
import java.util.*

data class ListTvShow(
    @SerializedName("results")
    val tvShow: ArrayList<TvShow>
)