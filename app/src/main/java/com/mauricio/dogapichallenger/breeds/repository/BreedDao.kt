package com.mauricio.dogapichallenger.breeds.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Query("SELECT * from Breed order by id")
    fun getBreeds(): MutableList<Breed>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(breed: Breed)
}