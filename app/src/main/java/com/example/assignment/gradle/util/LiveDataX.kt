package com.example.assignment.gradle.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

internal inline fun <T> LiveData<T>.observeNotNull(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) = observe(lifecycleOwner) {
    if (it != null) {
        observer(it)
    }
}
