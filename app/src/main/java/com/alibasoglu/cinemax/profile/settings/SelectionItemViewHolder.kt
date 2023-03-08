package com.alibasoglu.cinemax.profile.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibasoglu.cinemax.databinding.ItemSelectionBinding

class SelectionItemViewHolder(
    private val binding: ItemSelectionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(selectionListItem: SelectionListItem) {
        with(binding.selectionItemView) {
            text = selectionListItem.getVisibleName(itemView.context)
            isSelected = selectionListItem.isSelected
        }
    }

    companion object {
        fun create(parent: ViewGroup): SelectionItemViewHolder {
            val binding = ItemSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SelectionItemViewHolder(binding)
        }
    }
}
