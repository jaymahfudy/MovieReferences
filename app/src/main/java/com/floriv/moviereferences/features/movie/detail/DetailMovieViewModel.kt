package com.floriv.moviereferences.features.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.floriv.moviereferences.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel
@Inject constructor(private val getDetailMovie: GetDetailMovie) : BaseViewModel() {
    private val _movie: MutableLiveData<DetailMovie> = MutableLiveData()
    val movie: LiveData<DetailMovie> = _movie

    fun getMovieDetail(id: String) {
        getDetailMovie(GetDetailMovie.Params(id), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleData
            )
        }
    }

    private fun handleData(detail: DetailMovie) {
        _movie.value = detail
    }
}