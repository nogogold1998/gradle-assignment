package com.example.assignment.gradle.data.repo

import com.example.assignment.gradle.data.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    val images: Flow<List<Image>>

    /** @return number of image files*/
    fun loadAllImages(): Int

    fun cancel()
}
