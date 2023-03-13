package com.alibasoglu.cinemax.moviedetail.ui.tvshow

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibasoglu.cinemax.profile.settings.SeasonListItem

class SeasonSelectionAdapter(
    private val onDifferentSelectionSelected: (SeasonListItem) -> Unit
) : RecyclerView.Adapter<SeasonItemViewHolder>() {

    private val list = mutableListOf<SeasonListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonItemViewHolder {
        return SeasonItemViewHolder.create(parent).apply {
            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val selectedListItem = list[bindingAdapterPosition]
                    if (selectedListItem.isSelected.not()) {
                        onDifferentSelectionSelected.invoke(selectedListItem)
                        deselectListItem()
                        selectedListItem.isSelected = true
                        notifyItemChanged(bindingAdapterPosition, SELECTION_CHANGED_PAYLOAD)
                    }
                }
            }
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SeasonItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItems(newList: List<SeasonListItem>) {
        list.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    private fun deselectListItem() {
        list.withIndex().find { (_, value) -> value.isSelected }?.run {
            value.isSelected = false
            notifyItemChanged(index, SELECTION_CHANGED_PAYLOAD)
        }
    }

    companion object {
        private const val SELECTION_CHANGED_PAYLOAD = "selection_changed_payload"
    }
}
