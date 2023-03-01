package com.alibasoglu.cinemax.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.databinding.ItemWishlistBinding
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import com.bumptech.glide.Glide

class WishListItemViewHolder(private val binding: ItemWishlistBinding) : ViewHolder(binding.root) {

    fun bind(wishListCardItem: WishListCardItem) {
        with(wishListCardItem) {
            val imageUrl = ImagesConfigData.secure_base_url + ImagesConfigData.backdrop_sizes?.get(1) + backdrop_path

            with(binding) {
                genreTextView.text = genre
                movieNameTextView.text = title
                ratingTextView.text = vote_average.toString().substring(0,3)
                typeTextView.text = "Movie"
                Glide.with(root.context).load(imageUrl).centerCrop().into(posterImageView)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): WishListItemViewHolder {
            return WishListItemViewHolder(
                ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            )
        }
    }
}
