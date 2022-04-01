package com.project.bitereg.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bitereg.auth.Authenticator
import com.project.bitereg.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    @Inject
    lateinit var auth: Authenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}