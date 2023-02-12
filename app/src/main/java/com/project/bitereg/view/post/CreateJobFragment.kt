package com.project.bitereg.view.post

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.bitereg.databinding.FragmentCreateJobBinding
import com.project.bitereg.models.JobOrIntern
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CreateJobFragment : BaseFragment<FragmentCreateJobBinding>() {

    private val postViewModel by viewModels<PostViewModel>()

    override fun inflate(): Inflate<FragmentCreateJobBinding> {
        return FragmentCreateJobBinding::inflate
    }

    override fun onViewCreated(binding: FragmentCreateJobBinding, savedInstanceState: Bundle?) {
        binding.publishBtn.setOnClickListener {
            if (validInput()) {
                toggleProgress(true)
                createPost()
            }
        }

        initFlows()
    }

    private fun initFlows() {
        lifecycleScope.launch {
            postViewModel.postCreationFlow.collect { result ->
                result?.let {
                    withContext(Dispatchers.Main) {
                        handlePostCreationResult(result)
                    }
                }
            }
        }
    }

    private fun validInput() = with(binding) {
        jobTitleInput.text.isNotBlank() &&
                jobTitleInput.text.isNotBlank() &&
                jobLinkInput.text.isNotBlank()
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
    }


    private fun createPost() = with(binding) {
        val post = JobOrIntern(
            companyName = companyName.text.toString(),
            registrationLink = jobLinkInput.text.toString(),
            title = jobTitleInput.text.toString()
        )

        postViewModel.createPost(post)
    }
}