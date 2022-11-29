package com.project.bitereg.view.post

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.bitereg.R
import com.project.bitereg.databinding.FragmentCreateQuoteBinding
import com.project.bitereg.models.Quote
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CreateQuoteFragment : BaseFragment<FragmentCreateQuoteBinding>() {

    private val postViewModel: PostViewModel by viewModels()

    override fun inflate(): Inflate<FragmentCreateQuoteBinding> {
        return FragmentCreateQuoteBinding::inflate
    }

    override fun onViewCreated(binding: FragmentCreateQuoteBinding, savedInstanceState: Bundle?) {
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
            Toast.makeText(context, "Quote was published successfully!", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        } else {
            result.exceptionOrNull()?.printStackTrace()
            Toast.makeText(
                context,
                "Something went wrong while publishing the Quote, try again.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun toggleProgress(isEnabled: Boolean) {
        binding.progressLayout.isVisible = isEnabled
        binding.submitBtn.isEnabled = !isEnabled
    }

    private fun initViews(binding: FragmentCreateQuoteBinding) = with(binding) {
        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        submitBtn.setOnClickListener {
            if (isValidInput()) {
                toggleProgress(true)
                publishQuote(
                    quoteInput.editText?.text.toString(),
                )
            } else {
                Toast.makeText(context, getString(R.string.verify_both_filled), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isValidInput(): Boolean {
        return CommonUtils.verifyInput(
            type = CommonUtils.VerificationType.BLANK_CHECK,
            inputLayout = binding.quoteInput,
            shouldShowErrorMessage = false
        )
    }

    private fun publishQuote(message: String) {
        postViewModel.createPost(
            Quote(message)
        )
    }

}