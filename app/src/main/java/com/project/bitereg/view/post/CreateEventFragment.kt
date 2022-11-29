package com.project.bitereg.view.post

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.project.bitereg.databinding.FragmentCreateEventBinding
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.PostViewModel
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateEventFragment : BaseFragment<FragmentCreateEventBinding>(),
    EasyPermissions.PermissionCallbacks {

    private val postViewModel by viewModels<PostViewModel>()

    override fun inflate(): Inflate<FragmentCreateEventBinding> {
        return FragmentCreateEventBinding::inflate
    }

    override fun onViewCreated(binding: FragmentCreateEventBinding, savedInstanceState: Bundle?) {
        initViews(binding)
        initData()
    }

    private fun initData() {
        postViewModel.postBitmap.observe(requireActivity()) {
            updateImageView(it)
        }
        lifecycleScope.launch {
            postViewModel.postCreationFlow.collect {
                it?.let { handlePostCreationResult(it) }
            }
        }
    }

    private fun updateImageView(uri: Uri) {
        Glide.with(binding.root).load(uri).into(binding.chooseImageIv)
        binding.chooseImgBtn.text = "Update Image"
    }

    private fun initViews(binding: FragmentCreateEventBinding) = with(binding) {
        chooseImgBtn.setOnClickListener { tryChoosingImage() }
        chooseImageIv.setOnClickListener { tryChoosingImage() }
        backBtn.setOnClickListener { requireActivity().onBackPressed() }
        publishBtn.setOnClickListener { handleEventCreation() }
    }

    private fun handleEventCreation() {
        if (isValid()) {
            toggleProgress(true)
            postViewModel.publishEvent(binding.eventUrlInput.editText?.text.toString())
        }
    }

    private fun handlePostCreationResult(result: Result<Boolean>) {
        toggleProgress(false)
        if (result.isSuccess) {
            Toast.makeText(context, "Notice was published successfully!", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        } else {
            result.exceptionOrNull()?.printStackTrace()
            Toast.makeText(
                context,
                "Something went wrong while publishing the Notice, try again.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun toggleProgress(isEnabled: Boolean) = with(binding) {
        progressLayout.isVisible = isEnabled
        publishBtn.isEnabled = !isEnabled
        backBtn.isEnabled = !isEnabled
        chooseImageIv.isEnabled = !isEnabled
        chooseImgBtn.isEnabled = !isEnabled
    }

    private fun isValid() = binding.eventUrlInput.editText?.text.toString().isNotBlank() &&
            postViewModel.isPosterSelected()


    private fun tryChoosingImage() {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            chooseImage()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Permission is required for uploading image",
                100,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun chooseImage() {
        val i = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        chooserIntentLauncher.launch(Intent.createChooser(i, "Select Event Poster"))
    }

    private val chooserIntentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val imageUri = it.data?.data
            if (imageUri != null) postViewModel.updateBitmap(imageUri)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Toast.makeText(context, "Permission is required for uploading image", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 100) chooseImage()
    }

}