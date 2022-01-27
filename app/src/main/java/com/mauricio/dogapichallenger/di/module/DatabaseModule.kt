package com.mauricio.dogapichallenger.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mauricio.dogapichallenger.breeds.repository.BreedDao
import com.mauricio.dogapichallenger.breeds.repository.BreedRoomDB
import com.mauricio.dogapichallenger.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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