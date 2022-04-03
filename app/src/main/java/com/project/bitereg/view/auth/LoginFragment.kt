package com.project.bitereg.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.bitereg.R
import com.project.bitereg.auth.firebaseimpl.AuthResponse
import com.project.bitereg.databinding.FragmentLoginBinding
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.view.activities.DashboardActivity
import com.project.bitereg.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        initLayout()
        setupFlow()

        CommonUtils.setupErrorState(binding.emailInput)
        CommonUtils.setupErrorState(binding.passwordInput)

        return binding.root
    }

    private fun initLayout() {
        binding.registerText.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.apply {
            signInBtn.setOnClickListener {
                if (!isValidInput()) return@setOnClickListener
                toggleViews(true)
                lifecycleScope.launch {
                    viewModel.loginUser(
                        email = binding.emailInput.editText?.text.toString(),
                        password = binding.passwordInput.editText?.text.toString()
                    )
                }
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}