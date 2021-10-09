package com.floriv.moviereferences.features.tvshow.detail

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.interactor.UseCase
import com.floriv.moviereferences.features.tvshow.TvShowRepository
import javax.inject.Inject

class GetDetailTvShow
@Inject constructor(private val tvShowRepository: TvShowRepository) :
    UseCase<DetailTvShow, GetDetailTvShow.Params>() {

    data class Params(val id: String)

    override suspend fun run(params: Params): Either<Failure, DetailTvShow> {
        return tvShowRepository.getTvShow(params.id)
    }
}