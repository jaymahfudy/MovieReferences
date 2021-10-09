package com.floriv.moviereferences.features.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.floriv.moviereferences.R
import com.floriv.moviereferences.core.exception.Failure
import com.floriv.moviereferences.core.extension.observe
import com.floriv.moviereferences.databinding.FragmentTrendingBinding
import com.floriv.moviereferences.features.MainActivity
import com.floriv.moviereferences.features.tvshow.list.TvShowFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    @Inject
    lateinit var rvTrendingAdapter: TrendingAdapter

    private val viewModel: TrendingViewModel by viewModels()

    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { columnCount = it.getInt(TvShowFragment.ARG_COLUMN_COUNT) }
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
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = rvTrendingAdapter
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData() {
        viewModel.getTrending()
    }

    private fun renderList(trending: Trending?) {
        rvTrendingAdapter.listTrending = trending?.results ?: emptyList()
        loadingState(false)
    }

    private fun handleFailure(failure: Failure?) {
        rvTrendingAdapter.listTrending = emptyList()
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
            TrendingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}