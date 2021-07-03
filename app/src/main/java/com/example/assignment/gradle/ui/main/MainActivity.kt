package com.example.assignment.gradle.ui.main

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.gradle.R
import com.example.assignment.gradle.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    @get:IdRes
    private val containerId: Int
        get() = R.id.fragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addHomeFragment()
    }

    private fun addHomeFragment() {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(containerId, homeFragment)
            .commit()
    }
}
