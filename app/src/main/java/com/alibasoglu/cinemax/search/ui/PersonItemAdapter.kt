package com.alibasoglu.cinemax.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alibasoglu.cinemax.search.ui.model.PersonItem
import com.alibasoglu.cinemax.utils.list.BaseDiffUtil

class PersonItemAdapter : ListAdapter<PersonItem, PersonItemViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonItemViewHolder {
        return PersonItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PersonItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
