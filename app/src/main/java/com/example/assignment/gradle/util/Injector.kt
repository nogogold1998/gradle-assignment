package com.example.assignment.gradle.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.gradle.data.repo.ImageRepository
import com.example.assignment.gradle.data.repo.ImageRepositoryImpl
import com.example.assignment.gradle.data.repo.LocalImageRepository
import com.example.assignment.gradle.data.repo.LocalImageRepositoryImpl
import com.example.assignment.gradle.ui.home.HomeViewModel

private lateinit var context: Context

internal object Injector {
    private val localImageRepo: LocalImageRepository by lazy {
        LocalImageRepositoryImpl(context.contentResolver)
    }

    internal val imageRepository: ImageRepository by lazy {
        ImageRepositoryImpl(localImageRepo)
    }

    internal val homeVMFactory: ViewModelProvider.Factory = HomeViewModel.Factory()
}

fun Application.inject() {
    context = this.applicationContext
}
