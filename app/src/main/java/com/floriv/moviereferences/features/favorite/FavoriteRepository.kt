package com.floriv.moviereferences.features.favorite

import com.floriv.moviereferences.features.movie.MovieR
import javax.inject.Inject

interface FavoriteRepository {
    fun all(): List<MovieR>

    class LocalDB @Inject constructor(
        private var service: FavoriteService
    ) : FavoriteRepository {
        override fun all(): List<MovieR> {
            return service.all
        }

    }
}