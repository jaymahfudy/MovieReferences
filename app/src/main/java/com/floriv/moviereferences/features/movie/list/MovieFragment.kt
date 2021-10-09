package com.floriv.moviereferences.features.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.floriv.moviereferences.R
import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.extension.failure
import com.floriv.moviereferences.core.extension.observe
import com.floriv.moviereferences.databinding.FragmentMovieBinding
import com.floriv.moviereferences.features.MainActivity
import com.floriv.moviereferences.features.movie.detail.DetailMovieFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment() {

    @Inject
    lateinit var rvMovieAdapter: RvMovieAdapter

    private val movieViewModel: MovieViewModel by viewModels()

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { columnCount = it.getInt(ARG_COLUMN_COUNT) }
        with(movieViewModel) {
            observe(movies, ::renderList)
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
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = rvMovieAdapter
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        movieViewModel.getMovies()
    }

    private fun renderList(listMovie: ListMovie?) {
        rvMovieAdapter.listMovies = listMovie?.movies ?: emptyList()
        rvMovieAdapter.clickListener = { movie ->
            val bundle = bundleOf(DetailMovieFragment.ARG_ID to movie.id)
            findNavController().navigate(R.id.navigation_movie_detail, bundle)
        }
        loadingState(false)
    }

    private fun handleFailure(failure: Failure?) {
        rvMovieAdapter.listMovies = emptyList()
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
        Snackbar.LENGTH_SHORT
    ).show()

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}