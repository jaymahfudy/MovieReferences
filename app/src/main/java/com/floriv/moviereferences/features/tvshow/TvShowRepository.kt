package com.floriv.moviereferences.features.tvshow

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.platform.NetworkHandler
import com.floriv.moviereferences.features.tvshow.detail.DetailTvShow
import com.floriv.moviereferences.features.tvshow.list.ListTvShow
import retrofit2.Call
import javax.inject.Inject

interface TvShowRepository {
    fun getTvShows(): Either<Failure, ListTvShow>
    fun getTvShow(id: String): Either<Failure, DetailTvShow>
    fun searchTvShows(query: String): Either<Failure, ListTvShow>

    class Network @Inject constructor(
        private var networkHandler: NetworkHandler,
        private var service: TvShowService
    ) : TvShowRepository {
        override fun getTvShows(): Either<Failure, ListTvShow> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> {
                    request(service.getTvShows(), { it })
                }
                false -> {
                    Either.Left(Failure.NetworkConnection)
                }
            }
        }

        override fun getTvShow(id: String): Either<Failure, DetailTvShow> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> {
                    request(service.getDetailTvShow(id), { it })
                }
                false -> {
                    Either.Left(Failure.NetworkConnection)
                }
            }
        }

        override fun searchTvShows(query: String): Either<Failure, ListTvShow> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.searchTvShow(query), { it })
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