package com.chenriquevz.domain.model

import androidx.room.*

@Entity
data class WhatsAppEntry(@PrimaryKey val phoneNumber: Int, val name: String)

@Entity
data class Categories(@PrimaryKey val categoryName: String)

@Entity
data class WhatsAppHistory(@PrimaryKey val timeStamp: Long, val phoneNumberHistory: Int)


@Entity(primaryKeys = ["phoneNumber", "categoryName"])
data class WhatsAppEntriesWithCategory(
    val whatsAppEntry: WhatsAppEntry,
    val categoryName: Categories
)

data class WhatsAppEntryWithCategoryAndTimeStamp(
    @Embedded val whatsAppEntry: WhatsAppEntry,
    @Relation(
        parentColumn = "phoneNumber",
        entityColumn = "phoneNumberHistory"
    )
    val categories: List<WhatsAppHistory>,

    @Relation(
        parentColumn = "phoneNumber",
        entityColumn = "categoryName",
        associateBy = Junction(WhatsAppEntriesWithCategory::class)
    )
    val timeStamps: List<Categories>
)


data class CategoriesAndWhatsappEntriesPair(
    @Embedded val category: Categories,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "phoneNumber",
        associateBy = Junction(WhatsAppEntriesWithCategory::class)
    )
    val phoneNumbers: List<WhatsAppEntry>
)

data class WhatsAppHistoryWithWhatsappEntryAndCategories(
    @Embedded val whatsAppHistory: WhatsAppHistory,
    @Relation(
        parentColumn = "phoneNumberHistory",
        entityColumn = "phoneNumber"
    )
    val whatsappEntry: WhatsAppEntry
)
