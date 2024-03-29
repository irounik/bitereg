package com.project.bitereg.view.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.project.bitereg.databinding.FragmentDetailInputBinding
import com.project.bitereg.models.UserDetails
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.utils.DataUtils
import com.project.bitereg.utils.SelectionBottomSheet
import com.project.bitereg.view.activities.DashboardActivity
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar.*

@AndroidEntryPoint
class DetailInputFragment : BaseFragment<FragmentDetailInputBinding>(),
    DatePickerDialog.OnDateSetListener {

    val viewModel by viewModels<AuthViewModel>()

    override fun inflate(): Inflate<FragmentDetailInputBinding> =
        FragmentDetailInputBinding::inflate

    override fun onViewCreated(binding: FragmentDetailInputBinding, savedInstanceState: Bundle?) {
        initLayout(binding)
    }

    private fun initLayout(binding: FragmentDetailInputBinding) = binding.run {
        setupInputs(courseInput, DataUtils.getCourses())
        setupInputs(branchInput, DataUtils.getBranches())
        setupInputs(batchInput, DataUtils.getBatches())
        setupInputs(genderInput, DataUtils.getGender())

        dobInput.editText?.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this@DetailInputFragment,
                getInstance().get(YEAR),
                getInstance().get(MONTH),
                getInstance().get(DAY_OF_MONTH)
            ).show()
        }

        inputLayoutLl.children.forEach {
            CommonUtils.setupErrorState((it as TextInputLayout))
        }

        submitBtn.setOnClickListener {
            if (!validInput()) return@setOnClickListener
            binding.progressBar.isVisible = true
            lifecycleScope.launch {
                viewModel.updateUserDetails(getUserDetailsFromInput()).onSuccess {
                    handleSuccess()
                }.onFailure {
                    handleFailure()
                }
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun handleSuccess() {
        requireActivity().apply {
            startActivity(Intent(this, DashboardActivity::class.java))
            this.finish()
        }
    }

    private fun handleFailure() {
        Snackbar.make(
            binding.root,
            "Something went wrong, please try again!",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun getUserDetailsFromInput(): UserDetails {
        binding.apply {
            return UserDetails(
                course = courseInput.getText(),
                branch = branchInput.getText(),
                batch = batchInput.getText().toInt(),
                dob = dobInput.getText(),
                gender = genderInput.getText(),
                phoneNumber = phoneInput.getText()
            )
        }
    }

    private fun TextInputLayout.getText(): String = editText?.text.toString()

    private fun setupInputs(
        textInputLayout: TextInputLayout,
        inputList: List<String>
    ) {
        textInputLayout.editText?.apply {
            setOnClickListener {
                SelectionBottomSheet.getInstance(
                    list = inputList,
                    onItemSelected = { this.setText(it) }
                ).show(parentFragmentManager, "fragment")
            }
        }
    }

    private fun validInput(): Boolean {
        binding.inputLayoutLl.children.forEach {
            if (!CommonUtils.verifyInput(
                    type = CommonUtils.VerificationType.BLANK_CHECK,
                    inputLayout = it as TextInputLayout
                )
            ) return false
        }
        return CommonUtils.verifyInput(
            type = CommonUtils.VerificationType.PHONE,
            inputLayout = binding.phoneInput
        )
    }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        val date = "$dayOfMonth-$month-$year"
        binding.dobInput.editText?.setText(date)
    }
}