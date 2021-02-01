package com.example.assignment.gradle.data.repo

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import com.example.assignment.gradle.data.model.Image
import com.example.assignment.gradle.util.resizeTo

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

        /** These two variants of retrieving bitmap work if bitmap is resized else leads to OutOfMemory */
        val imageUri = ContentUris.withAppendedId(uri, id)
        // val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        //     val a = ImageDecoder.createSource(resolver, imageUri)
        //     ImageDecoder.decodeBitmap(a)
        // } else {
        //     MediaStore.Images.Media.getBitmap(resolver, imageUri)
        // }

        /** Version 2 is shorter */
        val bitmap = resolver.openInputStream(imageUri).use(BitmapFactory::decodeStream)
        // add this line in "application" tag in manifest.xml
        // android:requestLegacyExternalStorage="true"
        // val data = cursor.getString(2) // add MediaStore.MediaColumns.DATA to var [projection]
        // val file = File(data).inputStream()
        // val bitmap = BitmapFactory.decodeStream(file)
        return Image(id, displayName, bitmap.resizeTo(100))
    }

    companion object {
        private var instance: LocalImageRepositoryImpl? = null
        internal fun getInstance(context: Context): LocalImageRepository =
            instance ?: synchronized(this) {
                instance ?: LocalImageRepositoryImpl(context.contentResolver).also { instance = it }
            }
    }
}
