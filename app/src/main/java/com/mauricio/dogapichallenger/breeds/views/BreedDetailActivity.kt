package com.mauricio.dogapichallenger.breeds.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mauricio.dogapichallenger.R
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.BreedResultElement
import com.mauricio.dogapichallenger.breeds.models.EXTRA_BREED
import com.mauricio.dogapichallenger.databinding.ActivityBreedDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityBreedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBreedFromIntent()?.let { element ->
            updateView(element)
        }
    }

    private fun getBreedFromIntent() = intent.getSerializableExtra(EXTRA_BREED)

    private fun updateView(element: Any) {

        when(element) {
            is Breed -> {
                binding.urlPhoto = element.image?.url
                binding.breedName.text = element.name
                binding.breedCategory.text = element.breedGroup
                binding.origin.text = element.origin
                binding.temperament.text = element.temperament
            }
            is BreedResultElement -> {
                binding.urlPhoto = element.url
                binding.breedName.text = element.breeds.get(0).name
                binding.breedCategory.text = element.breeds.get(0).breedGroup
                binding.origin.text = checkIsEmpty(element.breeds[0].origin)
                binding.temperament.text = element.breeds.get(0).temperament
            }
        }
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

    private fun checkIsEmpty(value: String?): String {
        if (value.isNullOrBlank()) return getString(R.string.is_not_available)
        return value
    }
}