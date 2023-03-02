package com.alibasoglu.cinemax.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.alibasoglu.cinemax.ui.model.MovieBigCardItem
import com.alibasoglu.cinemax.utils.list.BaseDiffUtil

class MovieBigCardItemAdapter(
    private val listener: MoviesBigCardAdapterListener
) : PagingDataAdapter<MovieBigCardItem, MovieBigCardItemViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBigCardItemViewHolder {
        return MovieBigCardItemViewHolder.create(parent, movieClickItem)
    }

    override fun onBindViewHolder(holder: MovieBigCardItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private val movieClickItem = MovieBigCardItemViewHolder.MovieBigCardItemClickListener { movieBigCardItem ->
        listener.onMovieClick(movieBigCardItem)
    }

    fun interface MoviesBigCardAdapterListener {
        fun onMovieClick(movieBigCardItem: MovieBigCardItem)
    }
}
