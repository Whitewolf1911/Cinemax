package com.alibasoglu.cinemax.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.alibasoglu.cinemax.utils.list.BaseDiffUtil

class MoviesBasicCardAdapter : PagingDataAdapter<MovieBasicCardItem, MovieBasicCardItemViewHolder>(BaseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBasicCardItemViewHolder {
        return MovieBasicCardItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieBasicCardItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
