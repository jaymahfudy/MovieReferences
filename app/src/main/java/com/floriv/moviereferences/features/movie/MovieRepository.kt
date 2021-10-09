package com.floriv.moviereferences.features.movie

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.platform.NetworkHandler
import com.floriv.moviereferences.features.movie.detail.DetailMovie
import com.floriv.moviereferences.features.movie.list.ListMovie
import retrofit2.Call
import javax.inject.Inject

interface MovieRepository {
    fun getMovies(): Either<Failure, ListMovie>
    fun getMovie(id: String): Either<Failure, DetailMovie>
    fun searchMovies(query: String): Either<Failure, ListMovie>

    class Network @Inject constructor(
        private var networkHandler: NetworkHandler,
        private var service: MovieService
    ) : MovieRepository {
        override fun getMovies(): Either<Failure, ListMovie> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> {
                    request(service.getMovies(), { it })
                }
                false -> {
                    Either.Left(Failure.NetworkConnection)
                }
            }
        }

        override fun getMovie(id: String): Either<Failure, DetailMovie> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> {
                    request(service.getDetailMovie(id), { it })
                }
                false -> {
                    Either.Left(Failure.NetworkConnection)
                }
            }
        }

        override fun searchMovies(query: String): Either<Failure, ListMovie> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.searchMovie(query), { it })
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body()!!)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }
    }
}