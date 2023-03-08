package com.chenriquevz.domain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chenriquevz.domain.model.*

@Database(
    entities = [
        WhatsAppContact::class,
        Category::class,
        WhatsAppHistory::class,
        WhatsAppContactWithCategory::class,
    ], version = 1
)
abstract class WhatsAppDataBase : RoomDatabase() {
    abstract fun whatsAppDao(): WhatsAppDao
}