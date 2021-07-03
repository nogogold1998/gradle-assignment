package com.example.assignment.gradle

import android.app.Application
import com.example.assignment.gradle.util.inject

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        inject()
    }
}
