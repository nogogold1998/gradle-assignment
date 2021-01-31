package com.example.assignment.gradle.data.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class Image(val id: Long, val displayName: String, val uri: Uri, val bitmap: Bitmap) {

    companion object {
        val DiffUtils by lazy {
            object : DiffUtil.ItemCallback<Image>() {
                override fun areItemsTheSame(old: Image, new: Image) = old.id == new.id

                override fun areContentsTheSame(old: Image, new: Image) = old == new
            }
        }
    }
}
