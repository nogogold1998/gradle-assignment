package com.example.assignment.gradle.data.repo

import android.database.Cursor
import com.example.assignment.gradle.data.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ImageRepositoryImpl private constructor(
    private val localImageRepo: LocalImageRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ImageRepository, CoroutineScope {
    override val coroutineContext: CoroutineContext = Job() + dispatcher

    private val _images = Channel<List<Image>>(Channel.CONFLATED)
    override val images: Flow<List<Image>> = _images.receiveAsFlow().flowOn(dispatcher)

    override fun loadAllImages(): Int {
        val cursor = localImageRepo.queryAllLocalImages() ?: return 0
        launch {
            try {
                iterateAndCollectImages(cursor)
            } finally {
                withContext(NonCancellable) {
                    cursor.close()
                }
            }
        }
        return cursor.count
    }

    override fun cancel() = coroutineContext.cancel()

    private suspend fun iterateAndCollectImages(cursor: Cursor) {
        val images = mutableListOf<Image>()
        if (cursor.moveToFirst()) {
            do {
                delay(50)
                images += localImageRepo.extractImage(cursor)
                _images.send(images)
            } while (cursor.moveToNext())
        }
    }

    companion object {
        private var instance: ImageRepositoryImpl? = null
        fun getInstance(
            localImageRepo: LocalImageRepository,
        ): ImageRepository = instance ?: synchronized(this) {
            instance ?: ImageRepositoryImpl(localImageRepo).also { instance = it }
        }
    }
}
