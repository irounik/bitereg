package com.project.bitereg.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.bitereg.databinding.FragmentAccountBinding
import com.project.bitereg.view.activities.AuthActivity
import com.project.bitereg.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.logoutBtn.setOnClickListener {
            lifecycleScope.launch {
                authViewModel.logoutUser()
                gotoAuthActivity()
            }
        }
        return binding.root
    }

    private fun gotoAuthActivity() {
        requireActivity().run {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}