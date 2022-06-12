package com.project.bitereg.view.issue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.bitereg.databinding.FragmentReportIssueBinding
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.viewmodel.IssueViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ReportIssueFragment : Fragment() {

    private var _binding: FragmentReportIssueBinding? = null
    private val binding: FragmentReportIssueBinding get() = _binding!!
    private val issueViewModel: IssueViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportIssueBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initFlows()
    }

    private fun initFlows() {
        lifecycleScope.launch {
            issueViewModel.issueCreationFlow.collect { result ->
                result?.let {
                    withContext(Dispatchers.Main) {
                        handleIssueCreationResult(result)
                    }
                }
            }
        }
    }

    private fun handleIssueCreationResult(result: Result<Boolean>) {
        toggleProgress(false)
        if (result.isSuccess) {
            Toast.makeText(context, "Issues was successfully reported!", Toast.LENGTH_SHORT).show()
        } else {
            result.exceptionOrNull()?.printStackTrace()
            Toast.makeText(
                context,
                "Something went wrong while reporting the issue, try again.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initViews() = binding.run {
        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        CommonUtils.setupErrorState(issueTitleInput)
        CommonUtils.setupErrorState(issueDescriptionInput)

        submitBtn.setOnClickListener {
            if (inputIsValid()) {
                toggleProgress(true)
                reportNewIssue()
            }
        }
    }

    private fun toggleProgress(isEnabled: Boolean) {
        binding.progressLayout.isVisible = isEnabled
        binding.submitBtn.isEnabled = !isEnabled
    }

    private fun reportNewIssue() {
        val title = binding.issueTitleInput.editText?.text.toString()
        val description = binding.issueDescriptionInput.editText?.text.toString()
        issueViewModel.reportIssue(title, description)
    }

    private fun inputIsValid(): Boolean {
        listOf(binding.issueTitleInput, binding.issueDescriptionInput).forEach {
            if (!CommonUtils.verifyInput(CommonUtils.VerificationType.BLANK_CHECK, it)) return false
        }
        return true
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}