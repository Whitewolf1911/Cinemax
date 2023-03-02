package com.alibasoglu.cinemax.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.databinding.ItemMovieBigCardBinding
import com.alibasoglu.cinemax.ui.model.MovieBigCardItem
import com.bumptech.glide.Glide

class MovieBigCardItemViewHolder(
    private val binding: ItemMovieBigCardBinding,
    private val listener: MovieBigCardItemClickListener
) : ViewHolder(binding.root) {

    fun bind(movieBigCardItem: MovieBigCardItem) {
        with(binding) {
            with(movieBigCardItem) {
                val posterUrl = ImagesConfigData.secure_base_url + ImagesConfigData.poster_sizes?.get(1) + poster_path

                nameTextView.text = title
                genreTextView.text = genre
                ratingTextView.text = vote_average.toString()
                yearTextView.text = release_date
                Glide.with(binding.root.context)
                    .load(posterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_cinemax)
                    .into(posterImageView)
                root.setOnClickListener {
                    listener.onClick(movieBigCardItem)
                }
            }
        }
    }

    fun interface MovieBigCardItemClickListener {
        fun onClick(movieBigCardItem: MovieBigCardItem)
    }

    companion object {
        fun create(parent: ViewGroup, listener: MovieBigCardItemClickListener): MovieBigCardItemViewHolder {
            return MovieBigCardItemViewHolder(
                ItemMovieBigCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                listener
            )
        }
    }
}
