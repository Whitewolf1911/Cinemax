package com.alibasoglu.cinemax.ui.wishlist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import com.alibasoglu.cinemax.utils.list.BaseDiffUtil

class WishListCardAdapter(
    private val listener: WishListCardAdapterListener
) : ListAdapter<WishListCardItem, WishListItemViewHolder>(BaseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListItemViewHolder {
        return WishListItemViewHolder.create(parent, wishListClickItem)
    }

    override fun onBindViewHolder(holder: WishListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private val wishListClickItem = object : WishListItemViewHolder.WishListItemClickListener {
        override fun onClick(wishListCardItem: WishListCardItem) {
            listener.onClick(wishListCardItem)
        }
        override fun onLongClick(wishListCardItem: WishListCardItem) {
            listener.onLongClick(wishListCardItem)
        }
    }

    interface WishListCardAdapterListener {
        fun onClick(wishListCardItem: WishListCardItem)
        fun onLongClick(wishListCardItem: WishListCardItem)
    }
}
