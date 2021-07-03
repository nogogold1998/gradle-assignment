package com.example.assignment.gradle.data.repo

import com.example.assignment.gradle.data.model.Image

interface ImageRepository {
    fun loadAllImages(collectImagesCount: (Int) -> Unit, collectImages: (List<Image>) -> Unit)

    fun cancel()
}
