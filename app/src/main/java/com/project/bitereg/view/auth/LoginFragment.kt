package com.project.bitereg.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.bitereg.R
import com.project.bitereg.auth.firebaseimpl.AuthResponse
import com.project.bitereg.databinding.FragmentLoginBinding
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.view.activities.DashboardActivity
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: AuthViewModel by viewModels()

    override fun inflate(): Inflate<FragmentLoginBinding> = FragmentLoginBinding::inflate

    override fun onViewCreated(binding: FragmentLoginBinding, savedInstanceState: Bundle?) {
        initLayout(binding)
        setupFlow()

        CommonUtils.setupErrorState(binding.emailInput)
        CommonUtils.setupErrorState(binding.passwordInput)
    }

    private fun initLayout(binding: FragmentLoginBinding) = with(binding) {
        registerText.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        signInBtn.setOnClickListener {
            if (!isValidInput()) return@setOnClickListener
            toggleViews(true)
            lifecycleScope.launch {
                viewModel.loginUser(
                    email = emailInput.editText?.text.toString(),
                    password = passwordInput.editText?.text.toString()
                )
            }
        }
    }

    private fun setupFlow() {
        lifecycleScope.launch { viewModel.authResultFlow.collect { updateUI(it) } }
    }

    private fun updateUI(authResponse: AuthResponse) {
        Log.d(SignUpFragment.TAG, "updateUI: $authResponse")
        when (authResponse) {
            is AuthResponse.Success -> {
                toggleViews(false)
                gotoDashboard()
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

    private fun gotoDashboard() {
        requireActivity().run {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    private fun toggleViews(loadingStateVisible: Boolean) {
        binding.apply {
            progressBar.isVisible = loadingStateVisible
            signInBtn.isEnabled = !loadingStateVisible
            emailInput.isClickable = !loadingStateVisible
            passwordInput.isClickable = !loadingStateVisible
        }
    }

    private fun isValidInput(): Boolean {
        if (!CommonUtils.verifyInput(
                type = CommonUtils.VerificationType.EMAIL,
                inputLayout = binding.emailInput
            )
        ) {
            Toast.makeText(requireContext(), "Invalid email!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!CommonUtils.verifyInput(
                type = CommonUtils.VerificationType.EMAIL,
                inputLayout = binding.emailInput
            )
        ) {
            Toast.makeText(requireContext(), "Invalid password!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}