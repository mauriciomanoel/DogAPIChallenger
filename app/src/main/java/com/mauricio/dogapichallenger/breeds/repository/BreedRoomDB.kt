package com.mauricio.dogapichallenger.breeds.repository

import androidx.room.*
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.Image

@Database(entities = [Image::class, Breed::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BreedRoomDB: RoomDatabase() {
    abstract fun breedDao(): BreedDao
}