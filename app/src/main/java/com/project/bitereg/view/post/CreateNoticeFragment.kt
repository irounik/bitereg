package com.project.bitereg.view.post

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.bitereg.R
import com.project.bitereg.databinding.FragmentCreateNoticeBinding
import com.project.bitereg.models.Notice
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CreateNoticeFragment : BaseFragment<FragmentCreateNoticeBinding>() {

    private val postViewModel: PostViewModel by viewModels()

    override fun inflate(): Inflate<FragmentCreateNoticeBinding> {
        return FragmentCreateNoticeBinding::inflate
    }

    override fun onViewCreated(binding: FragmentCreateNoticeBinding, savedInstanceState: Bundle?) {
        initViews(binding)
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

    private fun toggleProgress(isEnabled: Boolean) {
        binding.progressLayout.isVisible = isEnabled
        binding.submitBtn.isEnabled = !isEnabled
    }

    private fun initViews(binding: FragmentCreateNoticeBinding) = with(binding) {
        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        submitBtn.setOnClickListener {
            if (isValidInput()) {
                toggleProgress(true)
                publishNotice(
                    noticeTitleInput.editText?.text.toString(),
                    noticeDescriptionInput.editText?.text.toString()
                )
            } else {
                Toast.makeText(context, getString(R.string.verify_both_filled), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isValidInput(): Boolean {
        val isTitleValid = CommonUtils.verifyInput(
            type = CommonUtils.VerificationType.BLANK_CHECK,
            inputLayout = binding.noticeTitleInput,
            shouldShowErrorMessage = false
        )

        if (!isTitleValid) return false

        return CommonUtils.verifyInput(
            type = CommonUtils.VerificationType.BLANK_CHECK,
            inputLayout = binding.noticeDescriptionInput,
            shouldShowErrorMessage = false
        )
    }

    private fun publishNotice(title: String, noticeBody: String) {
        postViewModel.createPost(
            Notice(
                title = title,
                noticeBody = noticeBody,
            )
        )
    }
}