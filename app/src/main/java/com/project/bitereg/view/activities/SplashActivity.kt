package com.project.bitereg.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.project.bitereg.R
import com.project.bitereg.auth.Authenticator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME = 0L
    }

    @Inject
    lateinit var auth: Authenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        findViewById<ImageView>(R.id.splash_logo).startAnimation(
//            AnimationUtils.loadAnimation(this, R.anim.smooth_rotation).apply {
//                duration = SPLASH_TIME * 4
//            }
//        )

        lifecycleScope.launch {
            delay(SPLASH_TIME)
            val nextActivity = if (auth.isUserLoggedIn()) DashboardActivity::class.java
            else AuthActivity::class.java
            startActivity(Intent(this@SplashActivity, nextActivity))
            finish()
        }
    }
}