package com.mauricio.dogapichallenger.breeds.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.mauricio.dogapichallenger.R
import com.mauricio.dogapichallenger.breeds.Breed
import com.mauricio.dogapichallenger.breeds.EXTRA_BREED
import com.mauricio.dogapichallenger.databinding.ActivityBreedDetailBinding

class BreedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedDetailBinding

    private fun getBreedFromIntent() = intent.getSerializableExtra(EXTRA_BREED) as? Breed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityBreedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBreedFromIntent()?.let { breed ->
            updateView(breed)
//            Toast.makeText(this, "${breed}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateView(bread: Breed) {
        binding.urlPhoto = bread.image.url
        binding.breedName.text = bread.name
        binding.breedCategory.text = bread.breedGroup
        binding.origin.text = bread.origin
        binding.temperament.text = bread.temperament
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}