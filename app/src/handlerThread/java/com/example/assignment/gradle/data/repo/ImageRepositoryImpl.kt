package com.example.assignment.gradle.data.repo

import android.database.Cursor
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.example.assignment.gradle.data.model.Image

class ImageRepositoryImpl(private val localImageRepo: LocalImageRepository) : ImageRepository {

    private val handlerThread by lazy {
        HandlerThread(HANDLER_THREAD_NAME, Process.THREAD_PRIORITY_BACKGROUND)
            .apply(HandlerThread::start)
    }
    private val handler by lazy { Handler(handlerThread.looper) }
    private val mainHandler by lazy { Handler(Looper.getMainLooper()) }

    private var backgroundRunnable: Runnable? = null

    @MainThread
    override fun loadAllImages(
        collectImagesCount: (Int) -> Unit,
        collectImages: (List<Image>) -> Unit
    ) {
        backgroundRunnable?.let(handler::removeCallbacks)
        backgroundRunnable = Runnable {
            val cursor = localImageRepo.queryAllLocalImages()
            val count = cursor?.count ?: 0
            mainHandler.post { collectImagesCount(count) }
            if (cursor == null) return@Runnable

            iterateAndCollectImages(cursor, collectImages)
        }.also(handler::post)
    }

    @WorkerThread
    private fun iterateAndCollectImages(
        cursor: Cursor,
        collectImages: (List<Image>) -> Unit
    ) {
        val images = mutableListOf<Image>()
        if (cursor.moveToFirst()) {
            do {
                images += localImageRepo.extractImage(cursor)
                mainHandler.post { collectImages(images) }
            } while (cursor.moveToNext())
        }
    }

    @MainThread
    override fun cancel() {
        backgroundRunnable?.let(handler::removeCallbacks)
        backgroundRunnable = null
        handlerThread.quit()
    }

    private companion object {
        const val HANDLER_THREAD_NAME = "ImageRepository"
    }
}
