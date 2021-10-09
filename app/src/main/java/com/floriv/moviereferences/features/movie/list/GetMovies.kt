package com.floriv.moviereferences.features.movie.list

import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.functional.Either
import com.floriv.moviereferences.core.interactor.UseCase
import com.floriv.moviereferences.features.movie.MovieRepository
import javax.inject.Inject

class GetMovies
@Inject constructor(private val movieRepository: MovieRepository) :
    UseCase<ListMovie, GetMovies.Params>() {

    data class Params(val lang: String)

    override suspend fun run(params: Params): Either<Failure, ListMovie> {
        return movieRepository.getMovies()
    }
}