package com.mauricio.dogapichallenger.breeds.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.BreedResultElement
import com.mauricio.dogapichallenger.breeds.models.EXTRA_BREED
import com.mauricio.dogapichallenger.databinding.ActivityBreedDetailBinding
import com.mauricio.dogapichallenger.utils.TextUtils
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

    private fun getBreedFromIntent() = intent.getSerializableExtra(EXTRA_BREED)

    private fun updateView(element: Any) {

        when(element) {
            is Breed -> {
                with(binding) {
                    urlPhoto = element.image?.url
                    breedName.text = element.name
                    breedCategory.text = element.breedGroup
                    origin.text = TextUtils.checkIsEmpty(context, element.origin)
                    temperament.text = TextUtils.checkIsEmpty(context, element.temperament)
                }
            }
            is BreedResultElement -> {
                with(binding) {
                    urlPhoto = element.url
                    breedName.text = element.breeds.get(0).name
                    breedCategory.text = element.breeds.get(0).breedGroup
                    origin.text = TextUtils.checkIsEmpty(context, element.breeds[0].origin)
                    temperament.text = TextUtils.checkIsEmpty(context, element.breeds.get(0).temperament)
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