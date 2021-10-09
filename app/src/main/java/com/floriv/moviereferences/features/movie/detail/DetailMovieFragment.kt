package com.floriv.moviereferences.features.movie.detail

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
import com.floriv.moviereferences.databinding.FragmentDetailMovieBinding
import com.floriv.moviereferences.features.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    private var id: String? = null

    private val viewModel: DetailMovieViewModel by viewModels()
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
        }
        with(viewModel) {
            observe(movie, ::renderData)
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
        // Inflate the layout for this fragment
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        loadingState(true)
        viewModel.getMovieDetail(id!!)
    }

    private fun renderData(movie: DetailMovie?) {
        with(binding) {
            val sb = StringBuilder()
            sb.append(BuildConfig.IMAGE_URL).append(movie?.posterPath)
            ivPoster.loadFromUrl(sb.toString())
            tvTitle.text = movie?.title
            tvTagline.text = movie?.tagline
            tvRating.text = movie?.voteAverage.toString()
            tvDuration.text = movie?.runtime.toString()
            tvLang.text = movie?.originalLanguage
            genresTitle.text = movie?.genres?.map { it?.name }?.joinToString(", ")
            tvRelease.text = movie?.releaseDate
            tvShortDesc.text = movie?.overview

            if (movie?.genres?.isNotEmpty() == true) genresTitle.isVisible = true
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
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }
}