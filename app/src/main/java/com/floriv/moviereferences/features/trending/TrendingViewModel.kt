package com.floriv.moviereferences.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.floriv.moviereferences.core.platform.BaseViewModel
import com.floriv.moviereferences.features.tvshow.list.GetTvShow
import com.floriv.moviereferences.features.tvshow.list.ListTvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val getTrending: GetTrending) : BaseViewModel() {

    private val _trending: MutableLiveData<Trending> = MutableLiveData()
    val tvShows: LiveData<Trending> = _trending

    fun getTrending() {
        getTrending(GetTrending.Params(""), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleData
            )
        }
    }

    private fun handleData(list: Trending) {
        _trending.value = list
    }
}