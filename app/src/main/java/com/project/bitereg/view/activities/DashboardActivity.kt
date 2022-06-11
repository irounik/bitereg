package com.project.bitereg.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.bitereg.R
import com.project.bitereg.databinding.ActivityDashboardBinding
import com.project.bitereg.utils.NavBarController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), NavBarController {

    lateinit var binding: ActivityDashboardBinding
    override val bottomNav: () -> BottomNavigationView get() = { binding.bottomNav }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        (supportFragmentManager.findFragmentById(R.id.dashboard_fragment_container) as NavHostFragment).run {
            binding.bottomNav.setupWithNavController(navController)
        }
    }
}