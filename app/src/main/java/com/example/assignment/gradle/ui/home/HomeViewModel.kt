package com.example.assignment.gradle.ui.home

import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.gradle.data.model.Image

class HomeViewModel : ViewModel() {
    private val _numOfImageFiles = MutableLiveData<Int>()

    val numOfImageFiles: LiveData<Int>
        get() = _numOfImageFiles

    private val _imageFiles = MutableLiveData<List<Image>>()

    val imageFiles: LiveData<List<Image>>
        get() = _imageFiles

    fun loadImages() {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }
}
