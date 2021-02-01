package com.example.assignment.gradle.ui.home

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.assignment.gradle.R
import com.example.assignment.gradle.base.BaseFragment
import com.example.assignment.gradle.databinding.FragmentHomeBinding
import com.example.assignment.gradle.util.showToast

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    private fun requestSelfPermissions() = context?.let { ctx ->
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getImages()
                } else {
                    val errorMsg = getString(R.string.error_read_external_permission_not_granted)
                    ctx.showToast(errorMsg, Toast.LENGTH_LONG)
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                ctx,
                REQUIRED_PERMISSIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                getImages()
            }
            shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS) -> {
                val msg = getString(R.string.msg_read_external_permission_required)
                ctx.showToast(msg, Toast.LENGTH_LONG)
            }
            else -> {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }
    }

    // TODO: delete later
    private fun getImages() = context?.let { ctx ->
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
            )
        // Note: Call the query() method in a worker thread
        val cursor = ctx.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val count = it.count
            it.moveToLast()
            val id = it.getLong(0)
            val imgUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            val bitmap: Bitmap
            ctx.contentResolver.openInputStream(imgUri).use {
                bitmap = BitmapFactory.decodeStream(it)
            }
            // imageMain.setImageBitmap(bitmap)
        }
    }

    private companion object {
        const val REQUIRED_PERMISSIONS = Manifest.permission.READ_EXTERNAL_STORAGE
    }
}
