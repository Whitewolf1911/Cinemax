package com.alibasoglu.cinemax.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.databinding.ItemPersonBinding
import com.alibasoglu.cinemax.search.ui.model.PersonItem
import com.bumptech.glide.Glide

class PersonItemViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(personItem: PersonItem) {
        with(binding) {
            with(personItem) {
                val imageUrl =
                    ImagesConfigData.secure_base_url + ImagesConfigData.profile_sizes?.get(1) + personItem.profile_path
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_person_24)
                    .into(personImageView)

                nameTextView.text = name
            }
        }
    }


    companion object {
        fun create(parent: ViewGroup): PersonItemViewHolder {
            return PersonItemViewHolder(
                ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}
