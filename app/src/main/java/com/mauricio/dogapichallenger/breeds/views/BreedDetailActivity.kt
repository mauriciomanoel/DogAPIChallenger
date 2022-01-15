package com.mauricio.dogapichallenger.breeds.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.mauricio.dogapichallenger.R
import com.mauricio.dogapichallenger.breeds.Breed
import com.mauricio.dogapichallenger.breeds.EXTRA_BREED

class BreedDetailActivity : AppCompatActivity() {

    private fun getBreedFromIntent() = intent.getSerializableExtra(EXTRA_BREED) as? Breed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_breed_detail)

        getBreedFromIntent()?.let { breed ->
            Toast.makeText(this, "${breed}", Toast.LENGTH_SHORT).show()
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