package com.example.assignment.gradle.ui

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.assignment.gradle.R
import com.example.assignment.gradle.util.showToast

class MainActivity : AppCompatActivity() {
    private lateinit var imageMain: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageMain = findViewById(R.id.imageMain)
        findViewById<Button>(R.id.buttonMain).setOnClickListener {
            getImages()
        }

        requestSelfPermissions()
    }

    private fun requestSelfPermissions() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getImages()
                } else {
                    val errorMsg = getString(R.string.error_read_external_permission_not_granted)
                    showToast(errorMsg, Toast.LENGTH_LONG)
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                this,
                REQUIRED_PERMISSIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                getImages()
            }
            shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS) -> {
                val msg = getString(R.string.msg_read_external_permission_required)
                showToast(msg, Toast.LENGTH_LONG)
            }
            else -> {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }
    }

    private fun getImages() {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
            )
        // Note: Call the query() method in a worker thread
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use { cursor ->
            val count = cursor.count
            cursor.moveToLast()
            val id = cursor.getLong(0)
            val imgUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            val bitmap: Bitmap
            contentResolver.openInputStream(imgUri).use {
                bitmap = BitmapFactory.decodeStream(it)
            }
            imageMain.setImageBitmap(bitmap)
        }
    }

    private companion object {
        const val REQUIRED_PERMISSIONS = Manifest.permission.READ_EXTERNAL_STORAGE
    }
}
