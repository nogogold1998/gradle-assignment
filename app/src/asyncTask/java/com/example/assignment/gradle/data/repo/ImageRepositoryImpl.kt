package com.example.assignment.gradle.data.repo

import com.example.assignment.gradle.data.model.Image
import com.example.assignment.gradle.util.IterateAndCollectImagesAsyncTask

class ImageRepositoryImpl private constructor(
    private val localImageRepo: LocalImageRepository,
) : ImageRepository {
    private var task: IterateAndCollectImagesAsyncTask? = null

    override fun loadAllImages(
        collectImagesCount: (Int) -> Unit,
        collectImages: (List<Image>) -> Unit
    ) {
        val query = localImageRepo.queryAllLocalImages() ?: return
        val task = IterateAndCollectImagesAsyncTask(
            transform = localImageRepo::extractImage,
            collectImagesCount,
            collectImages,
        ).also { task = it }
        task.execute(query)
    }

    override fun cancel() {
        task?.cancel()
    }

    companion object {
        private var instance: ImageRepositoryImpl? = null
        fun getInstance(localImageRepo: LocalImageRepository): ImageRepository =
            instance ?: synchronized(this) {
                instance ?: ImageRepositoryImpl(localImageRepo).also { instance = it }
            }
    }
}
