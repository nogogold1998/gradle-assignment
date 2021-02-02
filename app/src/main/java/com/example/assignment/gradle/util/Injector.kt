package com.example.assignment.gradle.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.gradle.data.repo.ImageRepositoryImpl
import com.example.assignment.gradle.data.repo.LocalImageRepositoryImpl
import com.example.assignment.gradle.ui.home.HomeViewModel

private lateinit var context: Context

internal object Injector {
    private fun getLocalImageRepo() =
        LocalImageRepositoryImpl.getInstance(context)

    internal fun getImageRepository() =
        ImageRepositoryImpl.getInstance(getLocalImageRepo())

    internal fun getHomeVMFactory(): ViewModelProvider.Factory =
        HomeViewModel.Factory()
}

fun Application.inject() {
    context = this.applicationContext
}
