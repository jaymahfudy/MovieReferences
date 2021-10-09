package com.floriv.moviereferences.features.trending

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.interactor.UseCase
import javax.inject.Inject

class GetTrending
@Inject constructor(private val trendingRepository: TrendingRepository) :
    UseCase<Trending, GetTrending.Params>() {

    data class Params(val lang: String)

    override suspend fun run(params: Params): Either<Failure, Trending> {
        return trendingRepository.getTrending()
    }
}