package com.example.assignment.gradle.util

import android.content.Context
import com.example.assignment.gradle.data.repo.ImageRepositoryImpl
import com.example.assignment.gradle.data.repo.LocalImageRepositoryImpl

internal object Injector {
    internal fun getLocalImageRepo(context: Context) =
        LocalImageRepositoryImpl.getInstance(context)

    internal fun getImageRepository(context: Context) =
        ImageRepositoryImpl.getInstance(getLocalImageRepo(context))
}
