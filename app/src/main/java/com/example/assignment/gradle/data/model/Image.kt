package com.example.assignment.gradle.data.model

import android.graphics.Bitmap
import androidx.recyclerview.widget.DiffUtil

data class Image(
    val id: Long, val displayName: String,
    @Deprecated(
        "By directly storing a bitmap object may lead to OutOfMemory, if you contains them in" +
            " a large collection. Please consider using 3rd-party image loading library"
    )
    val bitmap: Bitmap? = null
) {

    companion object {
        val DiffUtils by lazy {
            object : DiffUtil.ItemCallback<Image>() {
                override fun areItemsTheSame(old: Image, new: Image) = old.id == new.id

                override fun areContentsTheSame(old: Image, new: Image) = old == new
            }
        }
    }
}
