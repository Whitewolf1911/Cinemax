package com.alibasoglu.cinemax.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alibasoglu.cinemax.databinding.ItemMovieBasicCardBinding
import com.alibasoglu.cinemax.ui.model.MovieBasicCardItem
import com.bumptech.glide.Glide

class MovieBasicCardItemViewHolder(private val binding: ItemMovieBasicCardBinding) : ViewHolder(binding.root) {

    fun bind(movieBasicCardItem: MovieBasicCardItem) {
        with(binding) {
            with(movieBasicCardItem) {
                titleTextView.text = title
                genreTextView.text = genre
                ratingTextView.text = rating
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .centerCrop()
                    .into(posterImageView)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieBasicCardItemViewHolder {
            return MovieBasicCardItemViewHolder(
                ItemMovieBasicCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}
