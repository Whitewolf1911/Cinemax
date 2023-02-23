package com.alibasoglu.cinemax.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alibasoglu.cinemax.databinding.ItemCarouselMovieBinding
import com.alibasoglu.cinemax.home.ui.model.CarouselMovieItem
import com.bumptech.glide.Glide

class CarouselMovieItemViewHolder(private val binding: ItemCarouselMovieBinding) : ViewHolder(binding.root) {

    fun bind(carouselMovieItem: CarouselMovieItem) {
        with(binding) {
            with(carouselMovieItem) {
                titleTextView.text = title
                upcomingDateTextView.text = upcomingDate
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .centerCrop()
                    .into(movieImageView)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): CarouselMovieItemViewHolder {
            return CarouselMovieItemViewHolder(
                ItemCarouselMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}
