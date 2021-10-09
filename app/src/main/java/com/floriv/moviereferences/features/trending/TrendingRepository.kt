package com.floriv.moviereferences.features.trending

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.platform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface TrendingRepository {
    fun getTrending(): Either<Failure, Trending>

    class Network @Inject constructor(
        private var networkHandler: NetworkHandler,
        private var service: TrendingService
    ) : TrendingRepository {
        override fun getTrending(): Either<Failure, Trending> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> {
                    request(service.getTrending(), { it })
                }
                false -> {
                    Either.Left(Failure.NetworkConnection)
                }
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