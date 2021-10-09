package com.floriv.moviereferences.features.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.floriv.moviereferences.core.platform.BaseViewModel
import com.floriv.moviereferences.features.movie.list.GetMovies
import com.floriv.moviereferences.features.movie.list.ListMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {
    private val _movies: MutableLiveData<ListMovie> = MutableLiveData()
    val movies: LiveData<ListMovie> = _movies

    fun getMovies() {
        getMovies(GetMovies.Params(""), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleData
            )
        }
    }

    private fun handleData(list: ListMovie) {
        _movies.value = list
    }
}