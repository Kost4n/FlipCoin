package com.yes.or.no.app

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yes.or.no.app.databinding.ActivityFlipBinding

class FlipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFlipBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navigatView

        val navController = findNavController(R.id.nav_activity_pets)
        navView.setupWithNavController(navController)
    }

}