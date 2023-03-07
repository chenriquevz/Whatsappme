package com.chenriquevz.domain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chenriquevz.domain.model.*

@Database(
    entities = [
        WhatsAppEntry::class,
        Categories::class,
        WhatsAppHistory::class,
        WhatsAppEntriesWithCategory::class,
    ], version = 1
)
abstract class WhatsAppDataBase : RoomDatabase() {
    abstract fun whatsAppDao(): WhatsAppDao
}