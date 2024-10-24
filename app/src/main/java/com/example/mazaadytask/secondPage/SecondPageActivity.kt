package com.example.mazaadytask.secondPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.mazaadytask.R
import com.example.mazaadytask.databinding.ActivitySecondPageBinding

class SecondPageActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySecondPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        binding.navView.getOrCreateBadge(R.id.navigation_messages).apply {
            number = 3
            backgroundColor = getColor(R.color.primary_500)
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_second_page)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_explore,
                 R.id.navigation_messages,
                R.id.navigation_profile
            )
        )
        navView.setupWithNavController(navController)
    }
}