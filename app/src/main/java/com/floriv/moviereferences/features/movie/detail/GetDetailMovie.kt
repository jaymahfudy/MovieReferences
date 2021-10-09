package com.floriv.moviereferences.features.movie.detail

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.interactor.UseCase
import com.floriv.moviereferences.features.movie.MovieRepository
import javax.inject.Inject

class GetDetailMovie
@Inject constructor(private val movieRepository: MovieRepository) :
    UseCase<DetailMovie, GetDetailMovie.Params>() {

    data class Params(val id: String)

    override suspend fun run(params: Params): Either<Failure, DetailMovie> {
        return movieRepository.getMovie(params.id)
    }
}