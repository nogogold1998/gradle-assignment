package com.example.assignment.gradle.util

import android.content.ContentResolver
import com.example.assignment.gradle.data.repo.LocalImageRepository
import com.example.assignment.gradle.data.repo.LocalImageRepositoryImpl

internal object Injector {
    fun getLocalImageRepo(resolver: ContentResolver): LocalImageRepository =
        LocalImageRepositoryImpl.getInstance(resolver)
}
