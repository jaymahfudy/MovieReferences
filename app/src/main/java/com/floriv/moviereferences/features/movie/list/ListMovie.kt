package com.floriv.moviereferences.features.movie.list

import com.google.gson.annotations.SerializedName
import java.util.*

data class ListMovie(
    @SerializedName("results")
    val movies: ArrayList<Movie>?
)