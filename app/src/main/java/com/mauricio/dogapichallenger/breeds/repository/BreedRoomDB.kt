package com.mauricio.dogapichallenger.breeds.repository

import android.content.Context
import androidx.room.*
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.Eight
import com.mauricio.dogapichallenger.breeds.models.Image
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Image::class, Breed::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BreedRoomDB: RoomDatabase() {
    abstract fun breedDao(): BreedDao

    companion object {
        @Volatile
        private var INSTANCE: BreedRoomDB? = null

        fun getDatabase(context: Context): BreedRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BreedRoomDB::class.java,
                    "breed_database"
                ).build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}