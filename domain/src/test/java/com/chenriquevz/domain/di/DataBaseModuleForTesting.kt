package com.chenriquevz.domain.di

import android.content.Context
import androidx.room.Room
import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.data.WhatsAppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataBaseModule::class])
class DataBaseModuleForTesting {

    @Provides
    fun provideLogDao(database: WhatsAppDataBase): WhatsAppDao {
        return database.whatsAppDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): WhatsAppDataBase {
        return  Room.inMemoryDatabaseBuilder(
            appContext, WhatsAppDataBase::class.java).allowMainThreadQueries().build()
    }

}