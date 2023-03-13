package com.alibasoglu.cinemax.moviedetail.ui.tvshow

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alibasoglu.cinemax.moviedetail.ui.model.EpisodeItem
import com.alibasoglu.cinemax.utils.list.BaseDiffUtil

class EpisodesAdapter : ListAdapter<EpisodeItem, EpisodeItemViewHolder>(BaseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeItemViewHolder {
        return EpisodeItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EpisodeItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
