package com.example.assignment.gradle.data.repo

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import com.example.assignment.gradle.data.model.Image

internal class LocalImageRepositoryImpl private constructor(
    private val resolver: ContentResolver,
) : LocalImageRepository {
    private val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val projection = arrayOf(
        MediaStore.MediaColumns._ID,            // 0
        MediaStore.MediaColumns.DISPLAY_NAME,   // 1
    )

    // Note: Call the query() method in a worker thread
    @WorkerThread
    override fun queryAllLocalImages(): Cursor? =
        resolver.query(uri, projection, null, null, null)

    @WorkerThread
    override fun extractImage(cursor: Cursor): Image {
        val id = cursor.getLong(0)
        val displayName = cursor.getString(1)
        val imageUri = ContentUris.withAppendedId(uri, id)
        val bitmap = resolver.openInputStream(imageUri).use(BitmapFactory::decodeStream)
        return Image(id, displayName, imageUri, bitmap)
    }

    companion object {
        private var instance: LocalImageRepositoryImpl? = null
        internal fun getInstance(contentResolver: ContentResolver): LocalImageRepository =
            instance ?: synchronized(this) {
                instance ?: LocalImageRepositoryImpl(contentResolver).also { instance = it }
            }
    }
}
