package com.example.assignment.gradle.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.gradle.data.repo.ImageRepositoryImpl
import com.example.assignment.gradle.data.repo.LocalImageRepositoryImpl
import com.example.assignment.gradle.ui.home.HomeViewModel

internal object Injector {
    private fun getLocalImageRepo(context: Context) =
        LocalImageRepositoryImpl.getInstance(context)

    private fun getImageRepository(context: Context) =
        ImageRepositoryImpl.getInstance(getLocalImageRepo(context))

    internal fun getHomeVMFactory(context: Context): ViewModelProvider.Factory =
        HomeViewModel.Factory(getImageRepository(context))
}
