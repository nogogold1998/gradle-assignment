package com.example.assignment.gradle.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.assignment.gradle.data.model.Image
import com.example.assignment.gradle.data.repo.ImageRepository
import com.example.assignment.gradle.util.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlin.math.ceil

class HomeViewModel(private val imageRepo: ImageRepository) : BaseHomeViewModel() {
    private val _numOfImageFiles = MutableLiveData<Int>()
    override val numOfImageFiles: LiveData<Int> = _numOfImageFiles

    override val imageFiles: LiveData<List<Image>> =
        imageRepo.images
            .onEach { imagesList ->
                val total = numOfImageFiles.value ?: 0
                val progress = if (total == 0) 0 else ceil(100f * imagesList.size / total).toInt()
                _progression.postValue(progress)
            }.asLiveData(Dispatchers.Default)

    private val _progression = MutableLiveData<Int>()
    override val progression: LiveData<Int> = _progression

    override fun loadImages() {
        imageRepo.loadAllImages().let(_numOfImageFiles::postValue)
    }

    override fun onCleared() {
        super.onCleared()
        imageRepo.cancel()
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(Injector.imageRepository) as T
        }
    }
}
