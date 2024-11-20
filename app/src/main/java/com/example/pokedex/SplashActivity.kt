package com.example.pokedex

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pokedex.ui.screens.SplashScreenContent
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashScreenContent()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

            val nextActivity = if (isLoggedIn) MainActivity::class.java else AuthActivity::class.java

            val intent = Intent(this, nextActivity)
            startActivity(intent)
            finish()
        }, 2000)
    }
}