package com.floriv.moviereferences.features.tvshow.list

import com.google.gson.annotations.SerializedName

data class TvShow constructor(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("overview")
    private val overview: String?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("release_date")
    val release: String?
)