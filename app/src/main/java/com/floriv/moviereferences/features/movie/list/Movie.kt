package com.floriv.moviereferences.features.movie.list

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("release_date")
    val release: String?
)