@file:Suppress("DEPRECATION")

package com.example.assignment.gradle.util

import android.database.Cursor
import android.os.AsyncTask
import com.example.assignment.gradle.data.model.Image

class IterateAndCollectImagesAsyncTask(
    private val transform: (row: Cursor) -> Image,
    private val collectNumOfImages: (fileCount: Int) -> Unit,
    private val collectImage: (images: List<Image>) -> Unit,
) : AsyncTask<Cursor, Unit, Unit>() {

    private val images = mutableListOf<Image>()

    fun execute(succeededQuery: Cursor) {
        executeOnExecutor(THREAD_POOL_EXECUTOR, succeededQuery)
    }

    fun cancel() {
        super.cancel(true)
    }

    override fun doInBackground(vararg params: Cursor?) {
        check(params.size == 1)
        val cursor = checkNotNull(params[0])
        collectNumOfImages.let { cursor.count.let(it) }
        cursor.moveToFirst()
        do {
            cursor.let {
                images.add(transform.invoke(it))
                publishProgress(Unit)
            }
        } while (cursor.moveToNext() && !isCancelled)
    }

    override fun onProgressUpdate(vararg values: Unit?) {
        super.onProgressUpdate(*values)
        collectImage.invoke(images)
    }
}
