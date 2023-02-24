package com.alibasoglu.cinemax.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.alibasoglu.cinemax.utils.list.BaseDiffUtil

class MoviesBasicCardAdapter : ListAdapter<MovieBasicCardItem, MovieBasicCardItemViewHolder>(BaseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBasicCardItemViewHolder {
        return MovieBasicCardItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieBasicCardItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
