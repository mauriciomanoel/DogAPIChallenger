package com.mauricio.dogbreedapi

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mauricio.dogbreedapi.breeds.models.EXTRA_BREED
import com.mauricio.dogbreedapi.breeds.models.IOnClickEvent
import com.mauricio.dogbreedapi.breeds.views.BreedDetailActivity
import com.mauricio.dogbreedapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    override fun onItemClicked(value: Any) {
        Intent(this, BreedDetailActivity::class.java).apply {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_BREED, value as Parcelable)
            putExtras(bundle)
            startActivity(this)
        }
    }
}