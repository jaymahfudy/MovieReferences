package com.floriv.moviereferences.features.tvshow.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.floriv.moviereferences.core.platform.BaseViewModel
import com.floriv.moviereferences.features.tvshow.list.GetTvShow
import com.floriv.moviereferences.features.tvshow.list.ListTvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel
@Inject constructor(private val getTvShow: GetTvShow) : BaseViewModel() {

    private val _tvShows: MutableLiveData<ListTvShow> = MutableLiveData()
    val tvShows: LiveData<ListTvShow> = _tvShows

    fun getTvShows() {
        getTvShow(GetTvShow.Params(""), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleData
            )
        }
    }

    private fun handleData(list: ListTvShow) {
        _tvShows.value = list
    }
}