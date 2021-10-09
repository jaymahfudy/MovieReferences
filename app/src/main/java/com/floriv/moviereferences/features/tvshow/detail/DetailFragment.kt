package com.floriv.moviereferences.features.tvshow.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.floriv.moviereferences.BuildConfig
import com.floriv.moviereferences.R
import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.extension.failure
import com.floriv.moviereferences.core.extension.loadFromUrl
import com.floriv.moviereferences.core.extension.observe
import com.floriv.moviereferences.databinding.FragmentDetailTvBinding
import com.floriv.moviereferences.features.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var id: String? = null

    private val viewModel: DetailTvViewModel by viewModels()
    private var _binding: FragmentDetailTvBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
        }
        with(viewModel) {
            observe(tvShow, ::renderData)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadData() {
        loadingState(true)
        viewModel.getTvShowDetail(id!!)
    }

    private fun renderData(tvShow: DetailTvShow?) {
        with(binding) {
            val sb = StringBuilder()
            sb.append(BuildConfig.IMAGE_URL).append(tvShow?.posterPath)
            ivPoster.loadFromUrl(sb.toString())
            tvTitle.text = tvShow?.name
            tvTagline.text = tvShow?.tagline
            tvRating.text = tvShow?.voteAverage.toString()
            tvDuration.text = tvShow?.episodeRunTime?.first().toString()
            tvLang.text = tvShow?.originalLanguage
            genresTitle.text = tvShow?.genres?.map { it?.name }?.joinToString(", ")
            tvRelease.text = tvShow?.firstAirDate
            tvShortDesc.text = tvShow?.overview

            if (tvShow?.genres?.isNotEmpty() == true) genresTitle.isVisible = true
        }
        loadingState(false)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> notify(R.string.failure_network_connection)
            is Failure.ServerError -> notify(R.string.failure_server_error)
            else -> notify(R.string.get_list_failed)
        }
        loadingState(false)
    }

    private fun loadingState(state: Boolean) {
        (requireActivity() as MainActivity).binding.pbMain.isVisible = state
    }

    private fun notify(@StringRes message: Int) = Snackbar.make(
        binding.root,
        message,
        Snackbar.LENGTH_LONG
    ).show()

    companion object {
        const val ARG_ID = "id"

        @JvmStatic
        fun newInstance(id: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }
}