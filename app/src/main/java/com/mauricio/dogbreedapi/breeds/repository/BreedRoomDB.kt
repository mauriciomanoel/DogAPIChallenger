package com.mauricio.dogbreedapi.breeds.repository

import androidx.room.*
import com.mauricio.dogbreedapi.breeds.models.Breed
import com.mauricio.dogbreedapi.breeds.models.Image

@Database(entities = [Image::class, Breed::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BreedRoomDB: RoomDatabase() {
    abstract fun breedDao(): BreedDao
}