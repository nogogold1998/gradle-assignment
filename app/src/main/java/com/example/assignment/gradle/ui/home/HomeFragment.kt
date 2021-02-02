package com.example.assignment.gradle.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.gradle.R
import com.example.assignment.gradle.base.BaseFragment
import com.example.assignment.gradle.databinding.FragmentHomeBinding
import com.example.assignment.gradle.util.Injector
import com.example.assignment.gradle.util.observeNotNull
import com.example.assignment.gradle.util.showToast
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeVM: BaseHomeViewModel by viewModels { Injector.getHomeVMFactory(requireContext()) }

    private val adapter by lazy { ImageAdapter() }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).also {
        it.recyclerImages.adapter = adapter
        it.recyclerImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestSelfPermissions()
    }

    override fun observeLiveData() = with(homeVM) {
        imageFiles.observeNotNull(viewLifecycleOwner) {
            viewModelScope.launch {
                adapter.submitList(it) {
                    adapter.notifyDataSetChanged()
                    binding.recyclerImages.smoothScrollToPosition(adapter.itemCount - 1)
                }
            }
        }
        numOfImageFiles.observeNotNull(viewLifecycleOwner) {
            binding.textNumOfImages.text = it.toString()
        }
        progression.observeNotNull(viewLifecycleOwner) {
            binding.textProgress.text = getString(R.string.format_percent_decimal, it)
            binding.progressCircular.progress = it
        }
    }

    private fun requestSelfPermissions() = context?.let { ctx ->
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    homeVM.loadImages()
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
                homeVM.loadImages()
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

    private companion object {
        const val REQUIRED_PERMISSIONS = Manifest.permission.READ_EXTERNAL_STORAGE
    }
}
