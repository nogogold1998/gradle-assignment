package com.example.assignment.gradle.data.repo

import com.example.assignment.gradle.data.model.Image
import com.example.assignment.gradle.util.IterateAndCollectImagesAsyncTask

class ImageRepositoryImpl(
    private val localImageRepo: LocalImageRepository,
) : ImageRepository {
    private var task: IterateAndCollectImagesAsyncTask? = null

    override fun loadAllImages(
        collectImagesCount: (Int) -> Unit,
        collectImages: (List<Image>) -> Unit
    ) = localImageRepo.queryAllLocalImages()?.let { query ->
        val task = IterateAndCollectImagesAsyncTask(
            transform = localImageRepo::extractImage,
            collectImagesCount,
            collectImages,
        ).also { task = it }
        task.execute(query)
    }
        ?: collectImagesCount(0)

    override fun cancel() {
        task?.cancel()
        task = null
    }
}
