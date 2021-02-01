package com.example.assignment.gradle.data.repo

import androidx.lifecycle.LiveData
import com.example.assignment.gradle.data.model.Image

interface ImageRepository {
    val totalNumOfImages: LiveData<Int>

    val images: LiveData<List<Image>>
}
