package com.example.assignment.gradle.data.repo

import android.database.Cursor
import com.example.assignment.gradle.data.model.Image

interface LocalImageRepository {
    fun queryAllLocalImages(): Cursor?

    fun extractImage(cursor: Cursor): Image
}
