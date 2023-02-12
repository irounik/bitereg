package com.project.bitereg.view.dashboard.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.bitereg.databinding.FragmentAccountBinding
import com.project.bitereg.view.activities.AuthActivity
import com.project.bitereg.view.base.BaseFragment
import com.project.bitereg.view.base.Inflate
import com.project.bitereg.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private val authViewModel by viewModels<AuthViewModel>()

    private fun gotoAuthActivity() {
        requireActivity().run {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

    override fun inflate(): Inflate<FragmentAccountBinding> {
        return FragmentAccountBinding::inflate
    }

    override fun onViewCreated(binding: FragmentAccountBinding, savedInstanceState: Bundle?) {
        binding.logOutBtn.setOnClickListener {
            lifecycleScope.launch {
                authViewModel.logoutUser()
                gotoAuthActivity()
            }
        }
    }

}