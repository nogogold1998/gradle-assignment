package com.example.assignment.gradle.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.assignment.gradle.data.model.Image
import com.example.assignment.gradle.data.repo.ImageRepository
import com.example.assignment.gradle.util.Injector

class HomeViewModel(private val imageRepo: ImageRepository) : BaseHomeViewModel() {
    private val _numOfImageFiles = MutableLiveData<Int>()
    override val numOfImageFiles: LiveData<Int> get() = _numOfImageFiles

    private val _imageFiles = MutableLiveData<List<Image>>()
    override val imageFiles: LiveData<List<Image>> get() = _imageFiles

    override val progression = numOfImageFiles.switchMap { total ->
        imageFiles.map { it.size * 100 / total }
    }

    override fun loadImages() {
        imageRepo.loadAllImages(
            collectImages = _imageFiles::postValue,
            collectImagesCount = _numOfImageFiles::postValue,
        )
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
