package com.mauricio.dogbreedapi.breeds.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mauricio.dogbreedapi.breeds.models.Breed

@Dao
interface BreedDao {
    @Query("SELECT * from Breed order by id")
    fun getBreeds(): MutableList<Breed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(breed: Breed)
}