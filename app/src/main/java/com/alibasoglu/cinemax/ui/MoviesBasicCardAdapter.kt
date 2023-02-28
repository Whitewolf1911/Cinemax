package com.alibasoglu.cinemax.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.alibasoglu.cinemax.utils.list.BaseDiffUtil

class MoviesBasicCardAdapter(
    private val listener: MoviesCardAdapterListener
) : PagingDataAdapter<MovieBasicCardItem, MovieBasicCardItemViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBasicCardItemViewHolder {
        return MovieBasicCardItemViewHolder.create(parent, movieClickItem)
    }

    override fun onBindViewHolder(holder: MovieBasicCardItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private val movieClickItem = MovieBasicCardItemViewHolder.MovieCardItemClickListener { movieBasicCardItem ->
        listener.onMovieClick(movieBasicCardItem)
    }

    fun interface MoviesCardAdapterListener {
        fun onMovieClick(movieBasicCardItem: MovieBasicCardItem)
    }
}
