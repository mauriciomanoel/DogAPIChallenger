package com.mauricio.dogbreedapi.di.module

import android.content.Context
import androidx.room.Room
import com.mauricio.dogbreedapi.breeds.repository.BreedDao
import com.mauricio.dogbreedapi.breeds.repository.BreedRoomDB
import com.mauricio.dogbreedapi.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideBreedDao(appDatabase: BreedRoomDB): BreedDao {
        return appDatabase.breedDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): BreedRoomDB {
        return Room.databaseBuilder(
            context.applicationContext,
            BreedRoomDB::class.java,
            Constant.DATABASE_NAME
        ).build()
    }
}