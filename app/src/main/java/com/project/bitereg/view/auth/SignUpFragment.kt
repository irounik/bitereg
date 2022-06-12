package com.project.bitereg.view.auth

import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.bitereg.R
import com.project.bitereg.auth.firebaseimpl.AuthResponse
import com.project.bitereg.databinding.FragmentSignUpBinding
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun inflate(): Inflate<FragmentSignUpBinding> = FragmentSignUpBinding::inflate

    override fun onViewCreated(binding: FragmentSignUpBinding, savedInstanceState: Bundle?) {
        initViews(binding)
        setupFlow()
    }

    private fun setupFlow() {
        lifecycleScope.launch {
            viewModel.authResultFlow.collect {
                updateUI(it)
            }
        }
    }

    private fun toggleViews(loadingStateVisible: Boolean) {
        binding.apply {
            progressBar.isVisible = loadingStateVisible
            signUpBtn.isEnabled = !loadingStateVisible

            inputLayoutLl.children.forEach {
                it.isClickable = !loadingStateVisible
            }
        }
    }

    private fun initViews(binding: FragmentSignUpBinding) = with(binding) {

        loginText.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }


        signUpBtn.setOnClickListener {
            if (isValidInput()) {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                viewModel.createUser(
                    fullName.editText!!.text.toString(),
                    emailInput.editText!!.text.toString(),
                    passwordInput.editText!!.text.toString()
                )
            } else {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
        }
    }

    private fun isValidInput(): Boolean {

        if (!CommonUtils.verifyInput(
                type = CommonUtils.VerificationType.USER_NAME,
                binding.fullName
            )
        ) return false

        if (!CommonUtils.verifyInput(
                type = CommonUtils.VerificationType.EMAIL,
                binding.emailInput
            )
        ) return false

        if (!CommonUtils.verifyInput(
                type = CommonUtils.VerificationType.PASSWORD,
                binding.passwordInput
            )
        ) return false

        if (binding.passwordInput.editText!!.text.toString()
            != binding.confirmPasswordInput.editText!!.text.toString()
        ) {
            CommonUtils.setupErrorState(inputLayout = binding.confirmPasswordInput)
            binding.confirmPasswordInput.error = "Password dose match!!"
            binding.confirmPasswordInput.requestFocus()
            return false
        }

        return true
    }

    private fun updateUI(authResponse: AuthResponse) {
        Log.d(TAG, "updateUI: $authResponse")
        when (authResponse) {
            is AuthResponse.Success -> {
                toggleViews(false)
                findNavController().navigate(R.id.action_signUpFragment_to_detailInputFragment)
            }

            is AuthResponse.Failure -> {
                authResponse.exception.printStackTrace()
                toggleViews(false)
                Toast
                    .makeText(requireContext(), authResponse.exception.message, Toast.LENGTH_SHORT)
                    .show()
            }

            is AuthResponse.Loading -> {
                toggleViews(true)
            }
            else -> Unit
        }
    }

    companion object {
        const val TAG = "SignUpFragment"
    }
}