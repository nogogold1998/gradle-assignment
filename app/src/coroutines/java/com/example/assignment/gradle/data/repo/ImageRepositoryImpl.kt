package com.example.assignment.gradle.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.gradle.data.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ImageRepositoryImpl(
    private val localImageRepo: LocalImageRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ImageRepository, CoroutineScope {
    override val coroutineContext: CoroutineContext = Job() + dispatcher

    private val _totalNumOfImages = MutableLiveData<Int>()

    override val totalNumOfImages: LiveData<Int>
        get() = _totalNumOfImages
    private val _images = MutableLiveData<List<Image>>()

    override val images: LiveData<List<Image>>
        get() = _images

    init {

    }
}
