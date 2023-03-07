package com.chenriquevz.domain.di

import android.content.Context
import androidx.room.Room
import com.chenriquevz.domain.data.WhatsAppDao
import com.chenriquevz.domain.data.WhatsAppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class DataBaseModule {

    @Provides
    fun provideLogDao(database: WhatsAppDataBase): WhatsAppDao {
        return database.whatsAppDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): WhatsAppDataBase {
        return Room.databaseBuilder(
            appContext,
            WhatsAppDataBase::class.java,
            "logging.db"
        ).build()
    }

}