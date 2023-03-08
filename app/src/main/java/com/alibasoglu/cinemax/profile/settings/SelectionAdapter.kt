package com.alibasoglu.cinemax.profile.settings

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SelectionAdapter<T : SelectionListItem>(
    private val onDifferentSelectionSelected: (T) -> Unit
) : RecyclerView.Adapter<SelectionItemViewHolder>() {

    private val list = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionItemViewHolder {
        return SelectionItemViewHolder.create(parent).apply {
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

    override fun onBindViewHolder(holder: SelectionItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItems(newList: List<T>) {
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
