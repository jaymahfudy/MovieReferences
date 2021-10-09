package com.floriv.moviereferences.features.tvshow.list

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
import com.floriv.moviereferences.core.extension.observe
import com.floriv.moviereferences.databinding.FragmentTvshowBinding
import com.floriv.moviereferences.features.MainActivity
import com.floriv.moviereferences.features.movie.list.MovieFragment
import com.floriv.moviereferences.features.tvshow.detail.DetailFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    @Inject
    lateinit var rvTvShowAdapter: RvTvShowAdapter

    private val viewModel: TvShowViewModel by viewModels()

    private var _binding: FragmentTvshowBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { columnCount = it.getInt(ARG_COLUMN_COUNT) }
        with(viewModel) {
            observe(tvShows, ::renderList)
            observe(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvshowBinding.inflate(inflater, container, false)
        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = rvTvShowAdapter
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        viewModel.getTvShows()
    }

    private fun renderList(listShows: ListTvShow?) {
        rvTvShowAdapter.listShows = listShows?.tvShow ?: emptyList()
        rvTvShowAdapter.clickListener = { tvShow ->
            val bundle = bundleOf(DetailFragment.ARG_ID to tvShow.id)
            findNavController().navigate(R.id.navigation_tvshow_detail, bundle)
        }
        loadingState(false)
    }

    private fun handleFailure(failure: Failure?) {
        rvTvShowAdapter.listShows = emptyList()
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
            TvShowFragment().apply {
                arguments = Bundle().apply {
                    putInt(MovieFragment.ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}