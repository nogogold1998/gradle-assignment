package com.example.assignment.gradle.util

import android.graphics.Bitmap

internal fun Bitmap.resizeTo(maxSize: Int): Bitmap {
    val ratio = width.toFloat() / height
    val newWidth: Int
    val newHeight: Int
    if (ratio > 1) {
        newWidth = maxSize
        newHeight = (newWidth / ratio).toInt()
    } else {
        newHeight = maxSize
        newWidth = (newHeight * ratio).toInt()
    }
    return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
}
