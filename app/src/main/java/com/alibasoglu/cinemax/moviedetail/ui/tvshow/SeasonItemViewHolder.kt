package com.alibasoglu.cinemax.moviedetail.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibasoglu.cinemax.databinding.ItemSeasonBinding
import com.alibasoglu.cinemax.profile.settings.SeasonListItem

class SeasonItemViewHolder(
    private val binding: ItemSeasonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(selectionListItem: SeasonListItem) {
        with(binding.selectionItemView) {
            text = selectionListItem.getVisibleName(itemView.context)
            isSelected = selectionListItem.isSelected
            textSize = if (isSelected) {
                24f
            } else {
                20f
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): SeasonItemViewHolder {
            val binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SeasonItemViewHolder(binding)
        }
    }
}
