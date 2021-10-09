package com.floriv.moviereferences.features.tvshow.list

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.interactor.UseCase
import com.floriv.moviereferences.features.tvshow.TvShowRepository
import javax.inject.Inject

class GetTvShow
@Inject constructor(private val movieRepository: TvShowRepository) :
    UseCase<ListTvShow, GetTvShow.Params>() {

    data class Params(val lang: String)

    override suspend fun run(params: Params): Either<Failure, ListTvShow> {
        return movieRepository.getTvShows()
    }
}