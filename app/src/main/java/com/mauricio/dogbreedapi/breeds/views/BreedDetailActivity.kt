package com.mauricio.dogbreedapi.breeds.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mauricio.dogbreedapi.breeds.models.Breed
import com.mauricio.dogbreedapi.breeds.models.BreedResultElement
import com.mauricio.dogbreedapi.breeds.models.EXTRA_BREED
import com.mauricio.dogbreedapi.databinding.ActivityBreedDetailBinding
import com.mauricio.dogbreedapi.utils.extensions.checkIsEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedDetailBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityBreedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        getBreedFromIntent()?.let { element ->
            updateView(element)
        }
    }

    private fun getBreedFromIntent() = intent.extras?.get(EXTRA_BREED)

    private fun updateView(element: Any) {

        when(element) {
            is Breed -> {
                with(binding) {
                    urlPhoto = element.image?.url
                    breedName.text = element.name
                    breedCategory.text = element.breedGroup
                    origin.text = element.origin?.checkIsEmpty(context)
                    temperament.text = element.temperament?.checkIsEmpty(context)
                }
            }
            is BreedResultElement -> {
                with(binding) {
                    urlPhoto = element.url
                    breedName.text = element.breeds[0].name
                    breedCategory.text = element.breeds[0].breedGroup
                    origin.text = element.breeds[0].origin?.checkIsEmpty(context)
                    temperament.text = element.breeds[0].temperament?.checkIsEmpty(context)
                }
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

}