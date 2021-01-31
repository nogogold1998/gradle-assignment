package com.example.assignment.gradle.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.assignment.gradle.data.model.Image
import com.example.assignment.gradle.databinding.ItemRecyclerImageBinding
import com.example.assignment.gradle.ui.home.ImageAdapter.ImageItemVH

class ImageAdapter : ListAdapter<Image, ImageItemVH>(Image.DiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemVH {
        val inflater = LayoutInflater.from(parent.context)
        return ImageItemVH(inflater)
    }

    override fun onBindViewHolder(holder: ImageItemVH, position: Int) {
        getItem(position).let(holder::bind)
    }

    class ImageItemVH private constructor(private val binding: ItemRecyclerImageBinding) :
        ViewHolder(binding.root) {

        constructor(
            inflater: LayoutInflater,
            parent: ViewGroup? = null,
            attachToRoot: Boolean = false
        ) : this(ItemRecyclerImageBinding.inflate(inflater, parent, attachToRoot))

        fun bind(item: Image) = with(binding) {
            textImageName.text = item.displayName
            imageFileContent.setImageBitmap(item.bitmap)
        }
    }
}
