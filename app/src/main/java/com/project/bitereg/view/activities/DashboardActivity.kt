package com.project.bitereg.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.bitereg.R
import com.project.bitereg.databinding.ActivityDashboardBinding
import com.project.bitereg.utils.NavBarController
import com.project.bitereg.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), NavBarController {

    lateinit var binding: ActivityDashboardBinding
    override val bottomNav: () -> BottomNavigationView get() = { binding.bottomNav }
    override fun onBottomNavToggle(isVisible: Boolean) {
        binding.createPostFab.isVisible = isVisible
    }

    private val homeViewModel: HomeViewModel by viewModels()

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

        binding.createPostFab.setOnClickListener {
            val createPostIntent = Intent(this, CreatePostActivity::class.java)
            startActivity(createPostIntent)
        }

        lifecycleScope.launch {
            binding.createPostFab.isVisible = homeViewModel.canUserPost()
        }
    }
}