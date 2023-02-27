package com.alibasoglu.cinemax.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.databinding.ItemMovieBasicCardBinding
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.bumptech.glide.Glide

class MovieBasicCardItemViewHolder(
    private val binding: ItemMovieBasicCardBinding,
    private val listener: MovieCardItemClickListener
) : ViewHolder(binding.root) {

    fun bind(movieBasicCardItem: MovieBasicCardItem) {
        with(binding) {
            with(movieBasicCardItem) {
                val posterUrl = ImagesConfigData.secure_base_url + ImagesConfigData.poster_sizes?.get(1) + imageUrl

                titleTextView.text = title
                genreTextView.text = genre
                ratingTextView.text = rating
                Glide.with(binding.root.context)
                    .load(posterUrl)
                    .centerCrop()
                    .into(posterImageView)
                root.setOnClickListener {
                    listener.onClick(movieBasicCardItem)
                }
            }
        }
    }

    fun interface MovieCardItemClickListener {
        fun onClick(movieBasicCardItem: MovieBasicCardItem)
    }

    companion object {
        fun create(parent: ViewGroup, listener: MovieCardItemClickListener): MovieBasicCardItemViewHolder {
            return MovieBasicCardItemViewHolder(
                ItemMovieBasicCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                listener
            )
        }
    }
}
