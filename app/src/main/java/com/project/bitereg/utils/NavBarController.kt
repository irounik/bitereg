package com.project.bitereg.utils

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

interface NavBarController {

    val bottomNav: () -> BottomNavigationView

    fun onBottomNavToggle(isVisible: Boolean)

    fun hideNavBar() {
        bottomNav().visibility = View.GONE
        onBottomNavToggle(false)
    }

    fun showNavBar() {
        bottomNav().visibility = View.VISIBLE
        onBottomNavToggle(true)
    }
}