package com.project.bitereg.view.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.bitereg.R
import com.project.bitereg.auth.firebaseimpl.AuthResponse
import com.project.bitereg.databinding.FragmentSignUpBinding
import com.project.bitereg.utils.CommonUtils
import com.project.bitereg.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        binding.loginText.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        initViews()
        setupFlow()
        return binding.root
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initViews() {
        binding.signUpBtn.setOnClickListener {
            if (isValidInput()) {
                viewModel.createUser(
                    binding.emailInput.editText!!.text.toString(),
                    binding.passwordInput.editText!!.text.toString()
                )
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
                Log.d(TAG, "setupFlow: ${authResponse.authResult.user?.uid}")
                toggleViews(false)
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