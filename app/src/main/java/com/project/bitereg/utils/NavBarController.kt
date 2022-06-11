package com.project.bitereg.utils

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

interface NavBarController {

    val bottomNav: () -> BottomNavigationView

    fun hideNavBar() {
        bottomNav().visibility = View.GONE
    }

    fun showNavBar() {
        bottomNav().visibility = View.VISIBLE
    }
}