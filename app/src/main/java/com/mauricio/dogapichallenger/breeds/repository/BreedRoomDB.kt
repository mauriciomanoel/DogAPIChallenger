package com.mauricio.dogapichallenger.breeds.repository

import android.content.Context
import androidx.room.*
import com.mauricio.dogapichallenger.breeds.models.Breed
import com.mauricio.dogapichallenger.breeds.models.Image
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Image::class, Breed::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BreedRoomDB: RoomDatabase() {
    abstract fun breedDao(): BreedDao

    companion object {
        @Volatile
        private var INSTANCE: BreedRoomDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BreedRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BreedRoomDB::class.java,
                    "breed_database"
                )
//                    .addCallback(BreeItemCallback(scope))
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

//        private class BreeItemCallback(val scope: CoroutineScope):RoomDatabase.Callback(){
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//
//                INSTANCE?.let { foodItemRoomDB ->
//                    scope.launch {
//                        // if you want to populate database
//                        // when RoomDatabase is created
//                        // populate here
//                        foodItemRoomDB.breedDao() .insert(Image("1", 2, "Mango1",100))
//                        foodItemRoomDB.breedDao() .insert(Image("2", 3, "Mango2",100))
//                        foodItemRoomDB.breedDao() .insert(Image("3", 4, "Mango3",100))
//                    }
//                }
//            }
//        }
}