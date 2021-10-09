package com.floriv.moviereferences.features.tvshow.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.floriv.moviereferences.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel
@Inject constructor(private val getDetailTvShow: GetDetailTvShow) : BaseViewModel() {
    private val _tvShow: MutableLiveData<DetailTvShow> = MutableLiveData()
    val tvShow: LiveData<DetailTvShow> = _tvShow

    fun getTvShowDetail(id: String) {
        getDetailTvShow(GetDetailTvShow.Params(id), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleData
            )
        }
    }

    fun handleData(detail: DetailTvShow) {
        _tvShow.value = detail
    }
}