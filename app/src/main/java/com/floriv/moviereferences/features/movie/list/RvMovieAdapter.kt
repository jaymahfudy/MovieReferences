package com.floriv.moviereferences.features.movie.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.floriv.moviereferences.BuildConfig
import com.floriv.moviereferences.core.extension.loadFromUrl
import com.floriv.moviereferences.databinding.FragmentItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.properties.Delegates

class RvMovieAdapter @Inject constructor(
    @ApplicationContext val context: Context
) : RecyclerView.Adapter<RvMovieAdapter.ViewHolder>() {

    internal var listMovies: List<Movie> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (Movie) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listMovies[position]
        val sb = StringBuilder()
        sb.append(BuildConfig.IMAGE_URL).append(item.poster)
        holder.poster.loadFromUrl(sb.toString())
        holder.title.text = item.title
        holder.gridItem.setOnClickListener { clickListener(item) }
    }

    override fun getItemCount(): Int = listMovies.size

    inner class ViewHolder(binding: FragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val poster: ImageView = binding.logo
        val title: TextView = binding.itemTitle
        val gridItem: LinearLayout = binding.gridItem

    }

    companion object {
        private const val TAG = "RvMovieAdapter"
    }
}