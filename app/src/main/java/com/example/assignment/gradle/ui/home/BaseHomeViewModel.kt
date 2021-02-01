package com.example.assignment.gradle.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.gradle.data.model.Image

@Suppress("PropertyName")
abstract class BaseHomeViewModel : ViewModel() {
    abstract val numOfImageFiles: LiveData<Int>

    abstract val progression: LiveData<Int>

    abstract val imageFiles: LiveData<List<Image>>

    abstract fun loadImages()
}
