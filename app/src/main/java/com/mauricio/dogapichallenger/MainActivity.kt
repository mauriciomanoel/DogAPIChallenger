package com.mauricio.dogapichallenger

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mauricio.dogapichallenger.breeds.Breed
import com.mauricio.dogapichallenger.breeds.EXTRA_BREED
import com.mauricio.dogapichallenger.breeds.models.IOnClickEvent
import com.mauricio.dogapichallenger.breeds.views.BreedDetailActivity
import com.mauricio.dogapichallenger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IOnClickEvent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dog_breeds, R.id.navigation_search_screen
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onItemClicked(value: Breed) {
        val intent = Intent(this, BreedDetailActivity::class.java).apply {
            putExtra(EXTRA_BREED, value)
        }
        startActivity(intent)
    }

}